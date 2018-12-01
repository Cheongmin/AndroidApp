package com.cheongmin.voicereader.network

import com.cheongmin.voicereader.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private const val API_HOST = "http://ec2-13-209-22-141.ap-northeast-2.compute.amazonaws.com/api/v1/"
    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder().apply {
            client(createOkHttpClient())
            baseUrl(API_HOST)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build()
    }

    private fun accessTokenProvidingInterceptor(token: String?) = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", token)
                .build())
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }


    internal fun<T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    internal fun<T> createWithToken(service: Class<T>, token: String?): T {
        return Retrofit.Builder().apply {
            client(OkHttpClient.Builder().apply {
                addInterceptor(accessTokenProvidingInterceptor(token))
            }.build())
            baseUrl(API_HOST)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build().create(service)
    }

    internal fun<T> createWithBearerToken(service: Class<T>, token: String?): T {
        return Retrofit.Builder().apply {
            client(OkHttpClient.Builder().apply {
                addInterceptor(accessTokenProvidingInterceptor("Bearer " + token))
            }.build())
            baseUrl(API_HOST)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build().create(service)
    }

}