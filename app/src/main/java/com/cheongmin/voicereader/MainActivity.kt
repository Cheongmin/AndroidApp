package com.cheongmin.voicereader

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cheongmin.voicereader.model.Question
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.VoiceReaderService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        setupProfile()
        setupWelcomeMessage()
        setupInformation()

        btn_show_post_answer.setOnClickListener {
            val intent = Intent(this, PostAnswerActivity::class.java)
            startActivity(intent)
        }

        btn_show_post_question.setOnClickListener {
            val intent = Intent(this, PostQuestionActivity::class.java)
            startActivity(intent)
        }

        val apiClient = RetrofitManager.create(VoiceReaderService::class.java)
        apiClient.fetchQuestions(0, 3)
                .enqueue(object: Callback<List<Question>> {
                    override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                        Log.d("Retrofit", "call onResponse")
                        Log.d("Retrofit", response.message())
                        Log.d("Retrofit", response.body().toString())
                        if (response.isSuccessful) {
                            for (question in response.body().orEmpty()) {
                                Log.d("Retrofit", question.contents)
                            }
                        }
                    }
                    override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                        Log.d("Retrofit", "call onFailure")
                        t.printStackTrace()
                    }
                })
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
