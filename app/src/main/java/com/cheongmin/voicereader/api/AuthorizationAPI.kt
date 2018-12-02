package com.cheongmin.voicereader.api

import android.util.Log
import com.cheongmin.voicereader.model.AccessToken
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.AuthorizationService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthorizationAPI {
    fun fetchAccessToken(idToken: String): Single<AccessToken> {
        return Single.create { emitter ->
            val apiClient = RetrofitManager.create(AuthorizationService::class.java)
            apiClient.fetchAccessToken(idToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        emitter.onSuccess(it)
                    }, {
                        emitter.onError(it)
                    })
        }
    }

    fun refreshAccessToken(refreshToken: String) : Single<AccessToken> {
        return Single.create { emitter ->
            val apiClient = RetrofitManager.create(AuthorizationService::class.java)
            apiClient.refreshAccessToken("Bearer $refreshToken")
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