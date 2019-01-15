package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.AccessToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthorizationService {
    @GET("oauth2/token")
    fun fetchAccessToken(
    ) : Call<AccessToken>

    @POST("oauth2/token")
    fun refreshAccessToken(
    ) : Call<AccessToken>
}