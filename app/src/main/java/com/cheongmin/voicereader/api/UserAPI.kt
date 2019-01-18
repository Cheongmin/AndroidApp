package com.cheongmin.voicereader.api

import com.cheongmin.voicereader.model.response.Photo
import com.cheongmin.voicereader.model.response.User
import com.cheongmin.voicereader.model.request.UserRequest
import com.cheongmin.voicereader.network.client.ApiClient
import com.cheongmin.voicereader.network.client.AuthClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

object UserAPI {
  fun newUser(body: UserRequest): Single<User> {
    return Single.create { emitter ->
      AuthClient.userService
        .newUser(body)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
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
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
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
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
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
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }
}
