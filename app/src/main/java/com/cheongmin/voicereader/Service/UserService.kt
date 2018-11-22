package com.cheongmin.voicereader.Service

import com.cheongmin.voicereader.Model.Question
import com.cheongmin.voicereader.Model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("users")
    fun newUsers(
            @Body
            display_name: String
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