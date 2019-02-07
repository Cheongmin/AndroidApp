package com.cheongmin.voicereader.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.QuestionAPI
import com.cheongmin.voicereader.model.response.User
import com.cheongmin.voicereader.network.UserManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupActionBar()

    UserManager.user
      ?.let(this::setupUser)
      ?:run {
        AlertDialog.Builder(applicationContext, R.style.VoiceReader_AlertDialog).apply {
          setTitle("로그인 실패")
          setMessage("로그인을 다시 진행해주세요.")
          setCancelable(false)
          setPositiveButton("확인") { dialog, _ ->
            val intent = Intent(applicationContext, SplashActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
            finish()
          }
          show()
        }
      }

    setupInformation()

    btn_show_question_list.setOnClickListener {
      val intent = Intent(this, QuestionListActivity::class.java)
      startActivity(intent)
    }

    btn_show_post_question.setOnClickListener {
      val intent = Intent(this, PostQuestionActivity::class.java)
      startActivity(intent)
    }
  }

  private fun setupActionBar() {
    setSupportActionBar(toolbar)

    supportActionBar?.run {
      title = "홈"
      setDisplayShowTitleEnabled(true)
      setDisplayHomeAsUpEnabled(false)
    }
  }

  private fun setupUser(user: User) {
    if(user.profileUri.isNotEmpty()) {
      Picasso.get()
        .load(user.profileUri)
        .into(iv_user_profile)

      iv_user_profile.setOnClickListener {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("dataSource", user)
        startActivity(intent)
      }
    }

    tv_welcome_message.text = "${user.displayName}님 안녕하세요\n"
  }

  private fun setupInformation() {
    //TODO: 알림 개수와 활동 점수를 로컬 DB와 서버로부터 받아오도록 수정해야함
    val notificationCount = 0
    val actionPoint = 0

    tv_notification_count.text = notificationCount.toString()
    tv_action_point.text = actionPoint.toString()
  }
}
