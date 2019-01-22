package com.cheongmin.voicereader.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.auth0.android.jwt.JWT
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.network.client.ApiClient
import com.cheongmin.voicereader.network.client.AuthClient

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    AuthClient.init()

    val tokenManager = TokenManager.getInstance(applicationContext)

    val hasToken = tokenManager.hasToken() && tokenManager.hasRefreshToken()
    if (hasToken) {
      val token = tokenManager.getToken()

      val isExpired = JWT(token).isExpired(0)
      if (isExpired) {
        navigateToLoginActivity()
        return
      }

      ApiClient.init(token)
      Log.i("Token", token)

      navigateToMainActivity()

    } else {
      navigateToLoginActivity()
    }
  }

  private fun navigateToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
    finish()
  }

  private fun navigateToLoginActivity() {
    val intent = Intent(this, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
    finish()
  }

}
