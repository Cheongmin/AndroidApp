package com.cheongmin.voicereader.view

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.adapter.AnswerAdapter
import com.cheongmin.voicereader.adapter.QuestionAdapter
import com.cheongmin.voicereader.api.QuestionAPI
import com.cheongmin.voicereader.model.request.AnswerRequest
import com.cheongmin.voicereader.model.response.Question
import com.cheongmin.voicereader.network.UserManager
import com.cheongmin.voicereader.utils.DateUtils
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.layout_question_detail.*
import retrofit2.HttpException
import java.net.ProtocolException


class QuestionActivity : AppCompatActivity() {

  private lateinit var dataSource: Question

  private val compositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_question)
    setupActionBar()

    dataSource = intent.getSerializableExtra("dataSource") as Question
    dataSource.let {
      setupQuestion(it)
      setupAnswerList(it)
      setupSoundPlayer(it)
    }

    Log.i("Question ID", dataSource.id)

    btn_submit.setOnClickListener {
      val content: String = edit_answer.text.toString()
      postAnswer(content)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.dispose()
  }

  private fun setupActionBar() {
    setSupportActionBar(toolbar)

    supportActionBar?.run {
      title = "답변하기"
      setDisplayShowTitleEnabled(true)
      setDisplayHomeAsUpEnabled(true)
    }
  }

  private fun setupQuestion(question: Question) {
    tv_question_title.text = question.title
    tv_question_content.text = question.contents
    tv_question_date.text = DateUtils.getDateString(question.createdDate)

    iv_question_user_profile.setOnClickListener {
      val intent = Intent(this, ProfileActivity::class.java)
      intent.putExtra("dataSource", question.writer)
      startActivity(intent)
    }

    if(question.writer.profileUri.isNotEmpty()) {
      Picasso.get()
        .load(question.writer.profileUri)
        .into(iv_question_user_profile)
    }

    compositeDisposable.add(QuestionAPI.fetchQuestionById(question.id).subscribe({}, {}))

    if(question.writer.id != UserManager.user?.id) {
      iv_delete.visibility = View.GONE
    }

    iv_delete.setOnClickListener {
      AlertDialog.Builder(this).apply {
        setTitle("질문 삭제")
        setMessage("질문을 정말 삭제하시겠습니까?")
        setCancelable(true)
        setPositiveButton("삭제") { _, _ ->
          compositeDisposable.add(
            QuestionAPI.removeQuestion(question.id).subscribe({
              finish()
            }, {
              if (it is HttpException) {
                when(it.code()) {
                  202 -> {
                    finish()
                  }
                  404 -> {
                    Toast.makeText(context, "이미 삭제된 게시글입니다.", Toast.LENGTH_LONG).show()
                    finish()
                  }
                }
              } else if (it is ProtocolException) {
                Toast.makeText(context, "게시글이 삭제되었습니다.", Toast.LENGTH_LONG).show()
                finish()
              }
            })
          )
        }
        setNegativeButton("취소") { _, _ -> }
        show()
      }
    }

    tv_question_user_name.text = question.writer.displayName
    tv_question_user_location.text = question.writer.location
  }

  private fun setupAnswerList(question: Question) {
    val adapter = AnswerAdapter {
      val intent = Intent(this, ProfileActivity::class.java)
      intent.putExtra("dataSource", it)
      startActivity(intent)
    }
    rv_answers.adapter = adapter

    compositeDisposable.add(
      QuestionAPI.fetchAnswersByQuestionId(question.id)
        .subscribe({
          adapter.addItems(it)
          adapter.notifyDataSetChanged()
        }, {
          throw it
        })
    )
  }

  private fun setupSoundPlayer(question: Question) {
    sound_player.uri = question.soundUri
    sound_player.subtitles = question.subtitle
  }

  private fun postAnswer(content: String) {
    compositeDisposable.add(
      QuestionAPI.newAnswer(dataSource.id, AnswerRequest(content))
        .subscribe({
          val adapter = rv_answers.adapter as AnswerAdapter
          adapter.addItem(it)
          adapter.notifyItemInserted(adapter.itemCount)

          edit_answer.text.clear()
          edit_answer.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }, {
          throw it
        })
    )
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
