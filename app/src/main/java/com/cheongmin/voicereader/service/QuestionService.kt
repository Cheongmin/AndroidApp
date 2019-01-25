package com.cheongmin.voicereader.service

import com.cheongmin.voicereader.model.response.Answer
import com.cheongmin.voicereader.model.request.AnswerRequest
import com.cheongmin.voicereader.model.response.Question
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface QuestionService {
  @Multipart
  @POST("questions")
  fun newQuestion(
    @Part
    sound: MultipartBody.Part,
    @Part
    title: MultipartBody.Part,
    @Part
    content: MultipartBody.Part,
    @Part
    subtitles: MultipartBody.Part
  ): Single<Question>

  @GET("questions")
  fun fetchQuestions(
    @Query("offset")
    offset: Int,
    @Query("size")
    size: Int
  ): Single<List<Question>>

  @GET("questions/{question_id}")
  fun fetchQuestionById(
    @Path("question_id")
    question_id: String
  ): Single<Question>

  @DELETE("questions/{question_id}")
  fun removeQuestion(
    @Path("question_id")
    question_id: String
  ): Completable

  @POST("questions/{question_id}/answers")
  fun newAnswer(
    @Path("question_id")
    question_id: String,
    @Body
    content: AnswerRequest
  ): Single<Answer>

  @GET("questions/{question_id}/answers")
  fun fetchAnswersByQuestionId(
    @Path("question_id")
    question_id: String
  ): Single<List<Answer>>

  @GET("questions/{question_id}/answers/{answer_id}")
  fun fetchAnswerById(
    @Path("question_id")
    question_id: String,
    @Path("answer_id")
    answer_id: String
  ): Single<Answer>

  @DELETE("questions/{question_id}/answers/{answer_id}")
  fun removeAnswer(
    @Path("question_id")
    question_id: String,
    @Path("answer_id")
    answer_id: String
  ): Single<Void>
}