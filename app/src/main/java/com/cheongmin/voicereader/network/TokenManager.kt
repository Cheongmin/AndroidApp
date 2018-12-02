package com.cheongmin.voicereader.network

import android.content.Context
import android.preference.PreferenceManager
import com.cheongmin.voicereader.SingletonHolder
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.model.AccessToken
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val ACCESS_TOKEN = "auth.token"
const val REFRESH_TOKEN = "auth.refresh_token"

class TokenManager private constructor(context: Context) {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun getToken(): String {
        return preference.getString(ACCESS_TOKEN, "")
    }

    fun getRefreshToken(): String {
        return preference.getString(REFRESH_TOKEN, "")
    }

    fun setToken(token: AccessToken) {
        preference.edit()
                .putString(ACCESS_TOKEN, token.token)
                .putString(REFRESH_TOKEN, token.refreshToken)
                .commit()
    }

    fun setAccessToken(token: String) {
        preference.edit().putString(ACCESS_TOKEN, token).commit()
    }

    fun setRefreshToken(refreshToken: String) {
        preference.edit().putString(ACCESS_TOKEN, refreshToken).commit()
    }

    fun hasToken(): Boolean {
        return !getToken().isNullOrBlank()
    }

    fun hasRefreshToken(): Boolean {
        return !getToken().isNullOrBlank()
    }

    fun refreshToken(): Single<String> {
        return Single.create { emitter ->
            AuthorizationAPI.refreshAccessToken(getRefreshToken())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        setToken(it)
                        emitter.onSuccess(it.token)
                    }, {
                        emitter.onError(it)
                    })
        }
    }

    companion object: SingletonHolder<TokenManager, Context>(::TokenManager)
}