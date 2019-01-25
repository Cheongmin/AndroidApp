package com.cheongmin.voicereader.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import com.cheongmin.voicereader.utils.DateUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.layout_question_detail.*


class QuestionActivity : AppCompatActivity() {

  private lateinit var dataSource: Question

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

    if(question.writer.profileUri.isNullOrEmpty()) {
      Picasso.get()
        .load("https://app.voxeet.com/images/user-placeholder.png")
        .into(iv_question_user_profile)
    } else {
      Picasso.get()
        .load(question.writer.profileUri)
        .into(iv_question_user_profile)
    }

    tv_question_user_name.text = question.writer.displayName
    tv_question_user_location.text = question.writer.location
  }

  private fun setupAnswerList(question: Question) {
    val adapter = AnswerAdapter()
    rv_answers.adapter = adapter

    QuestionAPI.fetchAnswersByQuestionId(question.id)
      .subscribe({
        adapter.addItems(it)
        adapter.notifyDataSetChanged()
      }, {
      })

    sv_content.post {
      sv_content.fullScroll(View.FOCUS_UP)
    }
  }

  private fun setupSoundPlayer(question: Question) {
    sound_player.uri = question.soundUri
    sound_player.subtitles = question.subtitle
  }

  private fun postAnswer(content: String) {
    QuestionAPI.newAnswer(dataSource.id, AnswerRequest(content))
      .subscribe({
        val adapter = rv_answers.adapter as AnswerAdapter
        adapter.addItem(it)
        adapter.notifyItemInserted(adapter.itemCount)

        //TODO: Fix ScrollView scroll to bottom
        rv_answers.scrollToPosition(adapter.itemCount)

        edit_answer.text.clear()
        edit_answer.onEditorAction(EditorInfo.IME_ACTION_DONE)
      }, {
        Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
      })
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
