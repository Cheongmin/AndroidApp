package com.cheongmin.voicereader.api

import com.cheongmin.voicereader.model.Photo
import com.cheongmin.voicereader.model.User
import com.cheongmin.voicereader.model.UserRequest
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.UserService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

object UserAPI {
    fun newUsers(body: UserRequest): Single<User> {
        return Single.create { emitter ->
            RetrofitManager.create(UserService::class.java)
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
            RetrofitManager.create(UserService::class.java)
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
            RetrofitManager.create(UserService::class.java)
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
            RetrofitManager.create(UserService::class.java)
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
