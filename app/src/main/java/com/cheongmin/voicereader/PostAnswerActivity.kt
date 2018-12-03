package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_answer.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.cheongmin.voicereader.R.id.scroll
import com.cheongmin.voicereader.adapter.CommentAdapter
import com.cheongmin.voicereader.model.Comment
import com.cheongmin.voicereader.utils.DateUtils
import kotlinx.android.synthetic.main.layout_question.*
import java.util.*

class PostAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_answer)

        val index = intent.getIntExtra("index", 0)
        val article = db.articles[index]

        setupActionBar()
        setupProfile()

        tv_question_title.text = article.title
        tv_question_content.text = article.content
        tv_question_date.text = DateUtils.toDayText(article.date)

        tv_question_user_name.text = article.author.name
        tv_question_user_location.text = article.author.location
        Picasso.get()
                .load(article.author.profileUri)
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(iv_question_user_profile)

        val adapter = CommentAdapter()
        adapter.addItems(article.comments)
        adapter.notifyDataSetChanged()

        rv_answers.adapter = adapter

        sv_content.post {
            sv_content.fullScroll(View.FOCUS_UP)
        }

        btn_submit.setOnClickListener {
            val comment = Comment(db.users[0])
            comment.content = edit_answer.text.toString()
            comment.date = Date().time

            article.comments.add(comment)
            adapter.addItem(comment)
            adapter.notifyDataSetChanged()

            edit_answer.text.clear()
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
