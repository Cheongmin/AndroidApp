package com.cheongmin.voicereader.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.adapter.QuestionAdapter
import com.cheongmin.voicereader.api.QuestionAPI
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_question_list.*
import kotlinx.android.synthetic.main.include_toolbar.*

class QuestionListActivity : AppCompatActivity() {

  private val pagePerSize = 5

  private lateinit var layoutManager: LinearLayoutManager
  private lateinit var adapter: QuestionAdapter

  private var currentPage = 0

  private var isLoading = false
  private var isLastPage = false

  private val compositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_question_list)

    setupActionBar()
    setupQuestionList()
  }

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.dispose()
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
    adapter = QuestionAdapter {
      val intent = Intent(applicationContext, QuestionActivity::class.java)
      intent.putExtra("dataSource", it)
      startActivity(intent)
    }

    layoutManager = LinearLayoutManager(this)

    rv_questions.layoutManager = layoutManager
    rv_questions.adapter = adapter

    rv_questions.addOnScrollListener(object: RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (isLoading || isLastPage)
          return

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
          handleLoad(currentPage + 1)
        }
      }
    })

    refresh_layout.setOnRefreshListener {
      handleRefresh()
    }

    handleLoad(0)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }

  private fun handleLoad(page: Int) {
    isLoading = true

    val offset = page * pagePerSize
    val size = pagePerSize

    compositeDisposable.add(
      QuestionAPI.fetchQuestions(offset, size)
        .subscribe({
          if (it.isEmpty()) {
            isLastPage = true
            return@subscribe
          }

          adapter.addItems(it)
          adapter.notifyItemRangeInserted(adapter.itemCount, size)

          isLoading = false
          currentPage = page

          if(refresh_layout.isRefreshing)
            refresh_layout.isRefreshing = false

        }, {
          throw it
        })
    )
  }

  private fun handleRefresh() {
    val itemCount = adapter.itemCount
    adapter.clear()
    adapter.notifyItemRangeRemoved(0, itemCount)

    currentPage = 0
    isLastPage = false

    handleLoad(currentPage)
  }
}
