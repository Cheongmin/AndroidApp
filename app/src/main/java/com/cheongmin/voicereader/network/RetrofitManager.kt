package com.cheongmin.voicereader.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private const val API_HOST = "http://ec2-13-209-22-141.ap-northeast-2.compute.amazonaws.com/api/v1/"
    private const val ID_TOKEN = ""
    private const val ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJhMTc3OWU1MS1lMGRmLTQyMzgtOTMxMi0yOWNmN2JiMWY2NzciLCJleHAiOjE1NDI4OTM5MzYsImZyZXNoIjpmYWxzZSwiaWF0IjoxNTQyODA3NTM2LCJ0eXBlIjoiYWNjZXNzIiwibmJmIjoxNTQyODA3NTM2LCJpZGVudGl0eSI6IjViZjU1YjI4OGMxNzJlMDAwMWZhNDViMiJ9.s4wN48et5bJGRgoYLGH_g7CVtTHYlpoJ2HGQzXO1Bi4"

    private var client: OkHttpClient
    private var retrofit: Retrofit

    init {
        client = OkHttpClient.Builder().apply {
            addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val newRequest = chain.request().newBuilder().apply {
                        addHeader("Authorization", ACCESS_TOKEN)
                    }.build()
                    return chain.proceed(newRequest)
                }
            })
        }.build()

        retrofit = Retrofit.Builder().apply {
            client(client)
            baseUrl(API_HOST)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    fun accessTokenProvidingInterceptor() = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", ACCESS_TOKEN)
                .build())
    }

    internal fun<T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    internal fun<T> createWithAccessToken(service: Class<T>): T {
        return Retrofit.Builder().apply {
            client(OkHttpClient.Builder().apply {
                addInterceptor(accessTokenProvidingInterceptor())
            }.build())
            baseUrl(API_HOST)
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(service)
    }


}