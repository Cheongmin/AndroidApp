package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.Answer
import com.cheongmin.voicereader.model.AnswerRequest
import com.cheongmin.voicereader.model.Question
import com.cheongmin.voicereader.model.QuestionRequest
import io.reactivex.Single
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
            json: MultipartBody.Part
    ) : Single<Question>

    @GET("questions")
    fun fetchQuestions(
            @Query("offset")
            offset: Int,
            @Query("size")
            size: Int
    ) : Single<List<Question>>

    @GET("questions/{question_id}")
    fun fetchQuestionsById(
            @Path("question_id")
            question_id: String
    ) : Single<Question>

    @DELETE("questions/{question_id}")
    fun removeQuestion(
            @Path("question_id")
            question_id: String
    ) : Single<Void>

    @POST("/questions/{question_id}/answers")
    fun newAnswer(
            @Path("question_id")
            question_id: String,
            @Body
            body: AnswerRequest
    ) : Single<Answer>

    @GET("/questions/{question_id}/answers")
    fun fetchAnswersByQuestionId(
            @Path("question_id")
            question_id: String
    ) : Single<List<Answer>>

    @GET("/questions/{question_id}/answers/{answer_id}")
    fun fetchAnswerById(
            @Path("question_id")
            question_id: String,
            @Path("answer_id")
            answer_id: String
    ) : Single<Answer>

    @DELETE("/questions/{question_id}/answers/{answer_id}")
    fun removeAnswer(
            @Path("question_id")
            question_id: String,
            @Path("answer_id")
            answer_id: String
    ) : Single<Void>
}