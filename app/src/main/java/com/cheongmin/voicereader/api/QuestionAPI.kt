package com.cheongmin.voicereader.api

import com.cheongmin.voicereader.model.response.Answer
import com.cheongmin.voicereader.model.request.AnswerRequest
import com.cheongmin.voicereader.model.response.Question
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.network.client.ApiClient
import com.cheongmin.voicereader.service.QuestionService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

object QuestionAPI {
  fun newQuestion(sound: MultipartBody.Part, json: MultipartBody.Part): Single<Question> {
    return Single.create { emitter ->
      ApiClient.questionService
        .newQuestion(sound, json)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun fetchQuestions(offset: Int, size: Int): Single<List<Question>> {
    return Single.create { emitter ->
      ApiClient.questionService
        .fetchQuestions(offset, size)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun fetchQuestionById(questionId: String): Single<Question> {
    return Single.create { emitter ->
      ApiClient.questionService
        .fetchQuestionById(questionId)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun removeQuestion(questionId: String): Completable {
    return Completable.create { emitter ->
      ApiClient.questionService
        .removeQuestion(questionId)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onComplete()
        }, {
          emitter.onError(it)
        })
    }
  }

  fun newAnswer(questionId: String, body: AnswerRequest): Single<Answer> {
    return Single.create { emitter ->
      ApiClient.questionService
        .newAnswer(questionId, body)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun fetchAnswersByQuestionId(questionId: String): Single<List<Answer>> {
    return Single.create { emitter ->
      ApiClient.questionService
        .fetchAnswersByQuestionId(questionId)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun fetchAnswerById(questionId: String, answerId: String): Single<Answer> {
    return Single.create { emitter ->
      ApiClient.questionService
        .fetchAnswerById(questionId, answerId)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun removeAnswer(questionId: String, answerId: String): Completable {
    return Completable.create { emitter ->
      ApiClient.questionService
        .removeAnswer(questionId, answerId)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onComplete()
        }, {
          emitter.onError(it)
        })
    }
  }
}