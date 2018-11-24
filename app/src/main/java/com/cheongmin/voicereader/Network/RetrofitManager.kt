package com.cheongmin.voicereader.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private const val API_HOST = "http://ec2-13-209-22-141.ap-northeast-2.compute.amazonaws.com/api/v1/"

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder().apply {
            baseUrl(API_HOST)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    internal fun<T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}