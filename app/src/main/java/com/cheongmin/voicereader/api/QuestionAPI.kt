package com.cheongmin.voicereader.api

import com.cheongmin.voicereader.model.Answer
import com.cheongmin.voicereader.model.AnswerRequest
import com.cheongmin.voicereader.model.Question
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.QuestionService
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object QuestionAPI {
    fun newQuestion(accessToken: String?, sound: MultipartBody.Part, subtitles: MultipartBody.Part, contents: MultipartBody.Part, onSuccess: (response: Question?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.newQuestion(sound, subtitles, contents)
                .enqueue(object : Callback<Question> {
                    override fun onResponse(call: Call<Question>, response: Response<Question>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Question>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun fetchQuestions(accessToken: String?, offset: Int, size: Int, onSuccess: (response: List<Question>?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.fetchQuestions(offset, size)
                .enqueue(object : Callback<List<Question>> {
                    override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun fetchQuestionsById(accessToken: String?, question_id: String, onSuccess: (response: Question?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.fetchQuestionsById(question_id)
                .enqueue(object : Callback<Question> {
                    override fun onResponse(call: Call<Question>, response: Response<Question>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Question>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun removeQuestion(accessToken: String?, question_id: String, onSuccess: (response: Void?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.removeQuestion(question_id)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun newAnswer(accessToken: String?, question_id: String, body: AnswerRequest, onSuccess: (response: Answer?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.newAnswer(question_id, body)
                .enqueue(object : Callback<Answer> {
                    override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Answer>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun fetchAnswersByQuestionId(accessToken: String?, question_id: String, onSuccess: (response: List<Answer>?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.fetchAnswersByQuestionId(question_id)
                .enqueue(object : Callback<List<Answer>> {
                    override fun onResponse(call: Call<List<Answer>>, response: Response<List<Answer>>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<List<Answer>>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun fetchAnswerById(accessToken: String?, question_id: String, answer_id: String, onSuccess: (response: Answer?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.fetchAnswerById(question_id, answer_id)
                .enqueue(object : Callback<Answer> {
                    override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Answer>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }

    fun removeAnswer(accessToken: String?, question_id: String, answer_id: String, onSuccess: (response: Void?) -> Unit, onFailure: (throwable: Throwable) -> Unit){
        val apiClient = RetrofitManager.createWithBearerToken(QuestionService::class.java, accessToken)
        apiClient.removeAnswer(question_id, answer_id)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        onFailure.invoke(t)
                    }
                })
    }
}