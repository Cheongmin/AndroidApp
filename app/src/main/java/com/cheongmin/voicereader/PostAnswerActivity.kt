package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.include_toolbar.*

class PostAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_answer)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = "답변하기"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
