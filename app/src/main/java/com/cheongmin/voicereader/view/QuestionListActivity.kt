package com.cheongmin.voicereader.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.adapter.QuestionAdapter
import com.cheongmin.voicereader.api.QuestionAPI
import com.cheongmin.voicereader.model.response.Question
import kotlinx.android.synthetic.main.activity_question_list.*
import kotlinx.android.synthetic.main.include_toolbar.*

class QuestionListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_question_list)

    setupActionBar()
    setupQuestionList()
  }

  private fun setupActionBar() {
    setSupportActionBar(toolbar)

    supportActionBar?.run {
      title = "질문 목록"
      setDisplayShowTitleEnabled(true)
      setDisplayHomeAsUpEnabled(false)
    }
  }

  private fun setupQuestionList() {
    val adapter = QuestionAdapter()
    rv_questions.adapter = adapter

    QuestionAPI.fetchQuestions(0, 10)
      .subscribe({
        adapter.addItems(it)
        adapter.notifyDataSetChanged()
      }, {
        throw it
      })
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
