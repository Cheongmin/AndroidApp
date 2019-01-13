package com.cheongmin.voicereader.api

import com.cheongmin.voicereader.model.Answer
import com.cheongmin.voicereader.model.AnswerRequest
import com.cheongmin.voicereader.model.Question
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.QuestionService
import com.cheongmin.voicereader.service.UserService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object QuestionAPI {
  fun newQuestion(sound: MultipartBody.Part, json: MultipartBody.Part): Single<Question> {
    return Single.create { emitter ->
      RetrofitManager.create(QuestionService::class.java)
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
      RetrofitManager.create(QuestionService::class.java)
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
      RetrofitManager.create(QuestionService::class.java)
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
      RetrofitManager.create(QuestionService::class.java)
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
      RetrofitManager.create(QuestionService::class.java)
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
      RetrofitManager.create(QuestionService::class.java)
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
      RetrofitManager.create(QuestionService::class.java)
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
      RetrofitManager.create(QuestionService::class.java)
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