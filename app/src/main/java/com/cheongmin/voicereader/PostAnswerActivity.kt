package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cheongmin.voicereader.adapter.AnswerAdapter
import com.cheongmin.voicereader.model.Answer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_answer.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.cheongmin.voicereader.R.id.scroll



class PostAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_answer)

        setupActionBar()
        setupProfile()

        setupAnswerList()
    }

    private fun setupAnswerList() {
        val adapter = AnswerAdapter()

        for (i in 0..10) {
            adapter.addItem(Answer("000", "답변" + i.toString(), 0, "", i.toString()))
        }

        rv_answers.adapter = adapter

        sv_content.post {
            sv_content.fullScroll(View.FOCUS_UP)
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
