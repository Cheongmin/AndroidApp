package com.cheongmin.voicereader.api

import android.util.Log
import com.cheongmin.voicereader.UserInfo
import com.cheongmin.voicereader.model.AccessToken
import com.cheongmin.voicereader.model.User
import com.cheongmin.voicereader.model.UserRequest
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.AuthorizationService
import com.cheongmin.voicereader.service.UserService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object UserAPI {
    fun newUsers(idToken: String?, body: UserRequest, onSuccess: (response: User?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithToken(UserService::class.java, idToken)
        apiClient.newUsers(body)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun updateUser(accessToken: String?, userid: String, body: UserRequest, onSuccess: (response: Void?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(UserService::class.java, accessToken)
        apiClient.updateUser(userid, body)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun uploadUserPhoto(accessToken: String?, userid: String, photo: MultipartBody.Part, onSuccess: (response: Void?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(UserService::class.java, accessToken)
        apiClient.uploadUserPhoto(userid, photo)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun fetchUser(accessToken: String?, onSuccess:(response: User?)->Unit, onFailure:(throwable: Throwable)->Unit) {
        val apiClient = RetrofitManager.createWithBearerToken(UserService::class.java, accessToken)
        apiClient.fetchUser("5bf7647613a29200014e9a10")
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }
}
