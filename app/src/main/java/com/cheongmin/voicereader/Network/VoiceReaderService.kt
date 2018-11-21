package com.cheongmin.voicereader.Network

import com.cheongmin.voicereader.Model.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VoiceReaderService {
    @GET("questions")
    fun fetchQuestions(
            @Query("offset")
            offset: Int,
            @Query("size")
            size: Int
    ) : Call<List<Question>>
}