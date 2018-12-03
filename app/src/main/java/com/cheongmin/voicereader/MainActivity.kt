package com.cheongmin.voicereader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        btn_show_post_answer.setOnClickListener {
            val intent = Intent(this, PostAnswerActivity::class.java)
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
            title="홈"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupProfile(uri: String) {
        //TODO: 사용자 프로필 사진의 주소를 로컬DB 혹은 서버로부터 받아오도록 수정해야함
        Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(iv_user_profile)
    }

    private fun setupWelcomeMessage(name: String) {
        var message = name + "님 안녕하세요\n"
        message += "0개의 답변이 달렸습니다"

        tv_welcome_message.text = message
    }

    private fun setupInformation() {
        //TODO: 알림 개수와 활동 점수를 로컬 DB와 서버로부터 받아오도록 수정해야함
        val notificationCount = 0
        val actionPoint = 0

        tv_notification_count.text = notificationCount.toString()
        tv_action_point.text = actionPoint.toString()
    }
}
