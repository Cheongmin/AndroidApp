package com.cheongmin.voicereader.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.UserAPI
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.network.UserManager
import com.cheongmin.voicereader.network.client.ApiClient
import com.cheongmin.voicereader.network.client.AuthClient

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    AuthClient.init()

    val tokenManager = TokenManager.getInstance(applicationContext)

    val hasToken = tokenManager.isExists()
    if (hasToken) {
      val token = tokenManager.token
      val jwt = JWT(token)

      val isExpired = jwt.isExpired(0)
      if (isExpired) {
        navigateToLoginActivity()
        return
      }

      ApiClient.init(token)

      val id = jwt.getClaim("sub").asString()
      id?.let {
        UserAPI.fetchUser(it)
          .subscribe({ user ->
            UserManager.user = user
            navigateToMainActivity()
          }, {
            navigateToLoginActivity()
          })
      }
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
