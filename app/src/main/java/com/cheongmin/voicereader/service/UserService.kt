package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.Photo
import com.cheongmin.voicereader.model.UserRequest
import com.cheongmin.voicereader.model.User
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("users/")
    fun newUsers(
            @Body
            body: UserRequest
    ) : Single<User>

    @PUT("users/{userid}")
    fun updateUser(
            @Path("userid")
            userid: String,
            @Body
            body: UserRequest
    ) : Single<User>

    @Multipart
    @POST("users/{userid}")
    fun uploadUserPhoto(
            @Path("userid")
            userid: String,
            @Part
            photo: MultipartBody.Part
    ) : Single<Photo>

    @GET("users/{userid}")
    fun fetchUser(
            @Path("userid")
            userid: String
    ) : Single<User>
}