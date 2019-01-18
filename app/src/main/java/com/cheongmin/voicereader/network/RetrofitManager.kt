package com.cheongmin.voicereader.network

import com.cheongmin.voicereader.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
  private const val API_HOST = "http://ec2-13-209-22-141.ap-northeast-2.compute.amazonaws.com/api/v1/"

  fun create(): Retrofit {
    return Retrofit.Builder().apply {
      baseUrl(API_HOST)
      client(setupOkHttpClient())
      addConverterFactory(GsonConverterFactory.create())
      addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }.build()
  }

  fun createWithToken(token: String): Retrofit {
    return Retrofit.Builder().apply {
      baseUrl(API_HOST)
      client(setupOkHttpClientWithToken(token))
      addConverterFactory(GsonConverterFactory.create())
      addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }.build()
  }

  private fun setupOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
      if (BuildConfig.DEBUG)
        addInterceptor(createHttpLoggingInterceptor())
    }.build()
  }

  private fun setupOkHttpClientWithToken(token: String): OkHttpClient {
    return OkHttpClient.Builder().apply {
      addInterceptor(createTokenProvidingInterceptor(token))
      if (BuildConfig.DEBUG)
        addInterceptor(createHttpLoggingInterceptor())
    }.build()
  }

  private fun createTokenProvidingInterceptor(token: String) = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
      .addHeader("Authorization", "Bearer $token")
      .build())
  }

  private fun createHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

//  internal fun <T> create(authService: Class<T>): T {
//    return retrofit.create(authService)
//  }
}