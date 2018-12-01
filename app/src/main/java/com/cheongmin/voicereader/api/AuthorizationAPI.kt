package com.cheongmin.voicereader.api

import android.util.Log
import com.cheongmin.voicereader.model.AccessToken
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.AuthorizationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthorizationAPI {
    fun fetchAccessToken(idToken: String?, onSuccess:(response: AccessToken?)->Unit, onFailure:(throwable: Throwable)->Unit){
        val apiClient = RetrofitManager.createWithToken(AuthorizationService::class.java, idToken)
        apiClient.fetchAccessToken()
                .enqueue(object: Callback<AccessToken> {
                    override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                        Log.d("fetchAccessToken", "call onResponse")
                        Log.d("fetchAccessToken", response.message())
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }

                    override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                        Log.d("Retrofit", "call onFailure")
                        t.printStackTrace()
                        onFailure.invoke(t)
                    }
                })

    }

    fun refreshAccessToken(refreshToken: String?, onSuccess:(response: AccessToken?)->Unit, onFailure:(throwable: Throwable)->Unit){
        val apiClient = RetrofitManager.createWithBearerToken(AuthorizationService::class.java, refreshToken)
        apiClient.fetchAccessToken()
                .enqueue(object: Callback<AccessToken> {
                    override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }

                    override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                        Log.d("Retrofit", "call onFailure")
                        t.printStackTrace()
                        onFailure.invoke(t)
                    }
                })

    }
}