package com.cheongmin.voicereader

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cheongmin.voicereader.adapter.ArticleAdapter
import kotlinx.android.synthetic.main.activity_question_list.*
import kotlinx.android.synthetic.main.include_toolbar.*

class QuestionListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        setupUI()

        val adapter = ArticleAdapter {
            val intent = Intent(this, PostAnswerActivity::class.java)
            intent.putExtra("index", db.articles.indexOf(it))
            startActivity(intent)
        }
        adapter.addItems(db.articles)
        adapter.notifyDataSetChanged()

        list_questions.adapter = adapter
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title="질문 목록"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(false)
        }
    }
}
