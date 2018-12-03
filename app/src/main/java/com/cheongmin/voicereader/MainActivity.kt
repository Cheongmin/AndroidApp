package com.cheongmin.voicereader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cheongmin.voicereader.model.Article
import com.cheongmin.voicereader.model.Comment
import com.cheongmin.voicereader.model.User
import com.cheongmin.voicereader.utils.DateUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupData()

        val user = db.users[0]
        setupWelcomeMessage(user.name)
        setupProfile(user.profileUri)
        setupInformation()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title="홈"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(false)
        }

        btn_show_post_answer.setOnClickListener {
            val intent = Intent(this, QuestionListActivity::class.java)
            startActivity(intent)
        }

        btn_show_post_question.setOnClickListener {
            val intent = Intent(this, PostQuestionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupData() {
        var user1 = User()
        user1.name = "김질문"
        user1.location = "경기도 의정부시"
        user1.profileUri = "https://tinyfac.es/data/avatars/AEF44435-B547-4B84-A2AE-887DFAEE6DDF-200w.jpeg"

        var user2 = User()
        user2.name = "박답변"
        user2.location = "서울특별시 강남구"
        user2.profileUri = "https://pbs.twimg.com/profile_images/1006266234181210117/oedmUmVc.jpg"

        var user3 = User()
        user3.name = "전의사"
        user3.location = "@의정부언어치료센터"
        user3.profileUri = "https://pbs.twimg.com/profile_images/982738694116438017/-mLhSdy7.jpg"

        db.users.addAll(listOf(user1, user2, user3))

        var article1 = Article(user1)
        article1.title = "발음 교정 관련 질문입니다"
        article1.content = "얼마 후 면접이 있는데, 자기소개를 준비하고 있습니다. \n 내용이 잘 들리는지, 잘못 발음하는 부분이 없는지 확인 부탁드려요."
        article1.date = DateUtils.toLong(2018, 12, 1, 12, 30)

        var comment1_1 = Comment(user2)
        comment1_1.content = "발음보다도 전체적인 내용이 정리되지 않은 것 같아요."
        comment1_1.date = DateUtils.toLong(2018, 12, 1, 16, 0)

        var comment1_2 = Comment(user3)
        comment1_2.content = "중간 중간 비음이 너무 심해 발음이 명확하지 않게 들릴 수 있습니다.\n병원에 내원하셔서 간단한 검사를 받아보시는 걸 추천드립니다."
        comment1_2.date = DateUtils.toLong(2018, 12, 1, 18, 30)

        article1.comments.addAll(listOf(comment1_1, comment1_2))

        var article2 = Article(user1)
        article2.title = "언어 치료 질문이 있습니다"
        article2.content = "주변에서 계속 발음에 대해 지적해줘서, 언어 치료를 받아볼려고 하는데\n치료 효과가 어느정도인지 궁금합니다."
        article2.date = DateUtils.toLong(2018, 12, 2, 13, 20)

        var comment2_1 = Comment(user3)
        comment2_1.content = "현재 음성으로는 발음 방법부터 하나씩 짚어야할 것 같습니다.\n병원에 내원하여 더 자세한 상담해보시길 바랍니다."
        comment2_1.date = DateUtils.toLong(2018, 12, 2, 15, 30)

        article2.comments.addAll(listOf(comment2_1))

        db.articles.addAll(arrayOf(article1, article2))
    }

    private fun setupProfile(uri: String) {
        //TODO: 사용자 프로필 사진의 주소를 로컬DB 혹은 서버로부터 받아오도록 수정해야함
        Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(iv_user_profile)
    }

    private fun setupWelcomeMessage(name: String) {
        var message = name + "님 안녕하세요\n"
        message += "0개의 답변이 달렸습니다"

        tv_welcome_message.text = message
    }

    private fun setupInformation() {
        //TODO: 알림 개수와 활동 점수를 로컬 DB와 서버로부터 받아오도록 수정해야함
        val notificationCount = 0
        val actionPoint = 0

        tv_notification_count.text = notificationCount.toString()
        tv_action_point.text = actionPoint.toString()
    }
}
