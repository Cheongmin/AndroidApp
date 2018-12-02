package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.AccessToken
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AuthorizationService {
    @GET("token")
    fun fetchAccessToken(
            @Header("Authorization")
            idToken: String
    ): Single<AccessToken>

    @POST("token")
    fun refreshAccessToken(
            @Header("Authorization")
            refreshToken: String
    ): Single<AccessToken>
}