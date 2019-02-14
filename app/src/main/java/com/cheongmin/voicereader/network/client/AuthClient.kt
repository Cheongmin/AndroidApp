package com.cheongmin.voicereader.network.client

import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.AuthorizationService
import com.cheongmin.voicereader.service.UserService
import retrofit2.Retrofit

object AuthClient {
  private var retrofit: Retrofit? = null

  var authService: AuthorizationService? = null
  var userService: UserService? = null

  fun init() {
    retrofit = RetrofitManager.create()

    authService = retrofit?.create(AuthorizationService::class.java)
    userService = retrofit?.create(UserService::class.java)
  }
}