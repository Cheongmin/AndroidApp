package com.cheongmin.voicereader.api

import com.cheongmin.voicereader.model.response.AccessToken
import com.cheongmin.voicereader.network.client.AuthClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object AuthorizationAPI {
  fun fetchAccessToken(idToken: String): Single<AccessToken> {
    return Single.create { emitter ->
      AuthClient.service
        .fetchAccessToken(idToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          emitter.onSuccess(it)
        }, {
          emitter.onError(it)
        })
    }
  }

  fun refreshAccessToken(refreshToken: String): Single<AccessToken> {
    return Single.create { emitter ->
      AuthClient.service
        .refreshAccessToken("Bearer $refreshToken")
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