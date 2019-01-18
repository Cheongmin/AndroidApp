package com.cheongmin.voicereader.api

import com.cheongmin.voicereader.model.response.Photo
import com.cheongmin.voicereader.model.response.User
import com.cheongmin.voicereader.model.request.UserRequest
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.network.client.ApiClient
import com.cheongmin.voicereader.service.UserService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

object UserAPI {
  fun newUsers(body: UserRequest): Single<User> {
    return Single.create { emitter ->
      ApiClient.userService
        .newUsers(body)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun updateUser(userId: String, body: UserRequest): Single<User> {
    return Single.create { emitter ->
      ApiClient.userService
        .updateUser(userId, body)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun uploadUserPhoto(userId: String, photo: MultipartBody.Part): Single<Photo> {
    return Single.create { emitter ->
      ApiClient.userService
        .uploadUserPhoto(userId, photo)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun fetchUser(userId: String): Single<User> {
    return Single.create { emitter ->
      ApiClient.userService
        .fetchUser(userId)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }
}
