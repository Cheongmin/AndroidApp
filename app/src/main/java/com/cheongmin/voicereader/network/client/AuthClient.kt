package com.cheongmin.voicereader.network.client

import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.AuthorizationService
import com.cheongmin.voicereader.service.UserService
import retrofit2.Retrofit

object AuthClient {
  private lateinit var retrofit: Retrofit

  lateinit var authService: AuthorizationService
  lateinit var userService: UserService

  fun init() {
    retrofit = RetrofitManager.create()

    authService = retrofit.create(AuthorizationService::class.java)
    userService = retrofit.create(UserService::class.java)
  }
}