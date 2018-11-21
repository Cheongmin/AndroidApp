package com.cheongmin.voicereader.Retrofit.APIs

import com.cheongmin.voicereader.Retrofit.Models.TestResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class TestAPI {
    companion object {
        const val BASE_URL: String = "https://api.github.com/"
    }

    interface TestService {
        @GET("/search/users")
        fun apiDemo(): Call<TestResponseModel.TestResponse>

        /*
        @GET("api/")
        fun getIndex(@Query("text") text: String): Call<TextItem>

        @GET("token/")
        abstract fun getJSON(
                @Query("text") text: String
        ): Call<TokenResponseJSON>

        @POST("request_translate_v6/")
        abstract fun getJSON(@Body machineTranslationRequestJSON: MachineTranslationRequestJSON): Call<MachineTranslationResponseJSON>
        */
    }
}