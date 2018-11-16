package com.cheongmin.voicereader

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        setupProfile()
        setupWelcomeMessage()
        setupInformation()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title="홈"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupProfile() {
        //TODO: 사용자 프로필 사진의 주소를 로컬DB 혹은 서버로부터 받아오도록 수정해야함
        Picasso.get()
                .load("https://yt3.ggpht.com/-yOlMtZbsU4g/AAAAAAAAAAI/AAAAAAAAAAA/6W9wp1WvxIg/s76-c-k-no-mo-rj-c0xffffff/photo.jpg")
                .into(iv_user_profile)
    }

    private fun setupWelcomeMessage() {
        //TODO: 사용자의 이름과 알람 상태 등을 바탕으로 환영 메세지를 화면에 출력해야함
        val userName = "개발자"
        var message = userName + "님 안녕하세요\n"
        message += "1개의 답변이 달렸습니다"

        tv_welcome_message.text = message
    }

    private fun setupInformation() {
        //TODO: 알림 개수와 활동 점수를 로컬 DB와 서버로부터 받아오도록 수정해야함
        val notification_count = 1
        val action_point = 130

        tv_notification_count.text = notification_count.toString()
        tv_action_point.text = action_point.toString()
    }
}
