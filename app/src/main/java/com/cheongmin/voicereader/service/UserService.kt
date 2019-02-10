package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.response.Photo
import com.cheongmin.voicereader.model.request.UserRequest
import com.cheongmin.voicereader.model.response.User
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserService {
  @POST("users")
  fun newUser(
    @Header("Authorization")
    idToken: String,
    @Body
    body: UserRequest
  ): Single<User>

  @PUT("users/{userid}")
  fun updateUser(
    @Path("userid")
    userid: String,
    @Body
    body: UserRequest
  ): Single<User>

  @Multipart
  @POST("users/{userid}/photo")
  fun uploadUserPhoto(
    @Path("userid")
    userid: String,
    @Part
    photo: MultipartBody.Part
  ): Single<Photo>

  @GET("users/{userid}")
  fun fetchUser(
    @Path("userid")
    userid: String
  ): Single<User>
}