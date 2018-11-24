package com.cheongmin.voicereader.Service

import com.cheongmin.voicereader.Model.UserRequest
import com.cheongmin.voicereader.Model.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("users/")
    fun newUsers(
            @Body
            body: UserRequest
    ) : Call<User>

    @PUT("users/{userid}")
    fun updateUser(
            @Path("userid")
            userid: String,
            @Body
            body: UserRequest
    ) : Call<Void>

    @Multipart
    @POST("users/{userid}")
    fun uploadUserPhoto(
            @Path("userid")
            userid: String,
            @Part("photo")
            photo: MultipartBody.Part
    )

    @GET("users/{userid}")
    fun fetchUser(
            @Path("userid")
            userid: String
    ) : Call<User>
}