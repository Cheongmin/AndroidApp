package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_answer.*
import kotlinx.android.synthetic.main.include_toolbar.*

class PostAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_answer)

        setupActionBar()
        setupProfile()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = "답변하기"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupProfile() {
        //TODO: 사용자 프로필 사진의 주소를 로컬DB 혹은 서버로부터 받아오도록 수정해야함
        //Picasso.get()
        //        .load("https://yt3.ggpht.com/-yOlMtZbsU4g/AAAAAAAAAAI/AAAAAAAAAAA/6W9wp1WvxIg/s76-c-k-no-mo-rj-c0xffffff/photo.jpg")
        //        .into(iv_question_user_profile)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
