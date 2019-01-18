package com.cheongmin.voicereader.network.client

import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.QuestionService
import com.cheongmin.voicereader.service.UserService
import retrofit2.Retrofit

object ApiClient {
  private lateinit var retrofit: Retrofit

  lateinit var userService: UserService
  lateinit var questionService: QuestionService

  fun init(token: String) {
    retrofit = RetrofitManager.createWithToken(token)

    userService = retrofit.create(UserService::class.java)
    questionService = retrofit.create(QuestionService::class.java)
  }
}