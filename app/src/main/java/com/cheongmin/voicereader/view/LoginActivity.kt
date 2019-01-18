package com.cheongmin.voicereader.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.androidhuman.rxfirebase2.auth.*
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.network.client.ApiClient
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    btn_login.setOnClickListener {
      var email = edit_email.text.toString()
      var password = edit_password.text.toString()

      //TODO: Validate Email and Password
      handleLogin(email, password)
    }
  }

  private fun handleLogin(email: String, password: String) {
    val tokenManager = TokenManager.getInstance(applicationContext)

    FirebaseAuth.getInstance().rxSignInWithEmailAndPassword(email, password)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .flatMap { user -> user.rxGetIdToken(true) }
      .flatMap { token -> AuthorizationAPI.fetchAccessToken(token) }
      .subscribe({ accessToken ->
        tokenManager.setToken(accessToken)
        ApiClient.init(accessToken.token)
        navigateToMainActivity()
      }, {
        throw it
      })
  }

  private fun navigateToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
    finish()
  }
}
