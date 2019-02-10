package com.cheongmin.voicereader.network

import android.content.Context
import android.preference.PreferenceManager
import com.cheongmin.voicereader.SingletonHolder
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.model.response.AccessToken
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val ACCESS_TOKEN = "auth.token"
const val REFRESH_TOKEN = "auth.refresh_token"

class TokenManager private constructor(context: Context) {
  private val preference = PreferenceManager.getDefaultSharedPreferences(context)

  var token = preference.getString(ACCESS_TOKEN, "")!!
    set(value) {
      preference.edit()
        .putString(ACCESS_TOKEN, value)
        .commit()
    }

  var refreshToken = preference.getString(REFRESH_TOKEN, "")!!
    set(value) {
      preference.edit()
        .putString(REFRESH_TOKEN, value)
        .commit()
    }

  fun setToken(token: AccessToken) {
    preference.edit()
      .putString(ACCESS_TOKEN, token.token)
      .putString(REFRESH_TOKEN, token.refreshToken)
      .commit()
  }

  private fun hasToken(): Boolean {
    return !token.isBlank()
  }

  private fun hasRefreshToken(): Boolean {
    return !refreshToken.isBlank()
  }

  fun isExists(): Boolean {
    return hasToken() && hasRefreshToken()
  }

  fun refreshToken(): Single<String> {
    return Single.create { emitter ->
      AuthorizationAPI.refreshAccessToken(refreshToken)
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

  fun clear() {
    token = ""
    refreshToken = ""
  }

  companion object : SingletonHolder<TokenManager, Context>(::TokenManager)
}