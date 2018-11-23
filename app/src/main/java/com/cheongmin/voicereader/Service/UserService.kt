package com.cheongmin.voicereader.Service

import com.cheongmin.voicereader.Model.NewUserRequest
import com.cheongmin.voicereader.Model.Question
import com.cheongmin.voicereader.Model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("users")
    fun newUsers(
            @Body
            body: NewUserRequest
    ) : Call<User>

    @PUT
    fun updateUser(

    )

    @POST
    fun uploadUserPhoto(

    )

    @GET("users/{userid}")
    fun fetchUser(
            @Path("userid")
            userid: String
    ) : Call<User>
}