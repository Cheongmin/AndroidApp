package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.Answer
import com.cheongmin.voicereader.model.AnswerRequest
import com.cheongmin.voicereader.model.Question
import com.cheongmin.voicereader.model.QuestionRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface QuestionService {
    @Multipart
    @POST("questions")
    fun newQuestion(
            @Part
            sound: MultipartBody.Part,
            @Part
            subtitles: MultipartBody.Part,
            @Part
            contents: MultipartBody.Part
    ) : Call<Question>

    @GET("questions")
    fun fetchQuestions(
            @Query("offset")
            offset: Int,
            @Query("size")
            size: Int
    ) : Call<List<Question>>

    @GET("questions/{question_id}")
    fun fetchQuestionsById(
            @Path("question_id")
            question_id: String
    ) : Call<Question>

    @DELETE("questions/{question_id}")
    fun removeQuestion(
            @Path("question_id")
            question_id: String
    ) : Call<Void>

    @POST("/questions/{question_id}/answers")
    fun newAnswer(
            @Path("question_id")
            question_id: String,
            @Body
            body: AnswerRequest
    ) : Call<Answer>

    @GET("/questions/{question_id}/answers")
    fun fetchAnswersByQuestionId(
            @Path("question_id")
            question_id: String
    ) : Call<List<Answer>>

    @GET("/questions/{question_id}/answers/{answer_id}")
    fun fetchAnswerById(
            @Path("question_id")
            question_id: String,
            @Path("answer_id")
            answer_id: String
    ) : Call<Answer>

    @DELETE("/questions/{question_id}/answers/{answer_id}")
    fun removeAnswer(
            @Path("question_id")
            question_id: String,
            @Path("answer_id")
            answer_id: String
    ) : Call<Void>
}