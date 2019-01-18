package com.cheongmin.voicereader.network.client

import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.AuthorizationService
import retrofit2.Retrofit

object AuthClient {
  private lateinit var retrofit: Retrofit

  fun init() {
    retrofit = RetrofitManager.create()
  }

  val service: AuthorizationService = retrofit.create(AuthorizationService::class.java)
}