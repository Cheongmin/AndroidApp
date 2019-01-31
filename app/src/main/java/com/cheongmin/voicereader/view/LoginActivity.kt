package com.cheongmin.voicereader.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.androidhuman.rxfirebase2.auth.*
import com.auth0.android.jwt.JWT
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.api.UserAPI
import com.cheongmin.voicereader.model.response.AccessToken
import com.cheongmin.voicereader.model.response.User
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.network.UserManager
import com.cheongmin.voicereader.network.client.ApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import io.reactivex.Single
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

      //TODO: Implement Validate Email and Password
      edit_email.isEnabled = false
      edit_password.isEnabled = false
      btn_login.isEnabled = false

      handleLogin(email, password)
    }
  }

  private fun handleLogin(email: String, password: String) {
    edit_email.isEnabled = false
    edit_password.isEnabled = false
    btn_login.isEnabled = false

    val tokenManager = TokenManager.getInstance(applicationContext)
    FirebaseAuth.getInstance().rxSignInWithEmailAndPassword(email, password)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .flatMap { user -> user.rxGetIdToken(true) }
      .flatMap { token -> AuthorizationAPI.fetchAccessToken(token) }
      .flatMap {
        val userId = JWT(it.token).getClaim("sub").asString()
        return@flatMap Single.just(Pair(it, userId))
      }
      .flatMap { (token: AccessToken, userId: String?) ->
        tokenManager.setToken(token)
        ApiClient.init(token.token)

        return@flatMap UserAPI.fetchUser(userId!!)
      }
      .subscribe(this::handleLoginSuccess, this::handleLoginFailure)
  }

  private fun handleLoginSuccess(user: User) {
    UserManager.user = user
    navigateToMainActivity()
  }

  private fun handleLoginFailure(throwable: Throwable) {
    AlertDialog.Builder(this, R.style.VoiceReader_AlertDialog).apply {
      setTitle("로그인 실패")
      setMessage("이메일 또는 비밀번호를 다시 확인해주세요!")
      setCancelable(false)
      setPositiveButton("확인") { dialog, _ ->
        dialog.dismiss()
      }
      show()
    }

    edit_email.isEnabled = true
    edit_password.isEnabled = true
    btn_login.isEnabled = true
  }

  private fun navigateToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
    finish()
  }
}
