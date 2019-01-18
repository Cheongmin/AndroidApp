package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.response.AccessToken
import io.reactivex.Single
import retrofit2.http.*

interface AuthorizationService {
  @GET("oauth2/token")
  fun fetchAccessToken(
    @Header("Authorization")
    idToken: String
  ): Single<AccessToken>

  @POST("oauth2/token")
  fun refreshAccessToken(
    @Header("Authorization")
    refreshToken: String
  ): Single<AccessToken>
}