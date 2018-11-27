package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.AccessToken
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthorizationService {
    @GET("token")
    fun fetchAccessToken(
    ) : Single<AccessToken>

    @POST("token")
    fun refreshAccessToken(
    ) : Single<AccessToken>
}