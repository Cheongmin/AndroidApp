package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class PostQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_question)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title="질문하기"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(false)
        }
    }
}
