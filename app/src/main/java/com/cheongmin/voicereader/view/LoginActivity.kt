package com.cheongmin.voicereader.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.androidhuman.rxfirebase2.auth.*
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.network.TokenManager
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    btn_login.setOnClickListener { it ->
      var email = edit_email.text.toString()
      var password = edit_password.text.toString()
      FirebaseAuth.getInstance().rxSignInWithEmailAndPassword(email, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap { it.rxGetIdToken(true) }
        .subscribe({
          Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        }, {
          Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
        })
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode != RC_SIGN_IN)
      return

    val response: IdpResponse = IdpResponse.fromResultIntent(data)!!

    if (resultCode != Activity.RESULT_OK)
      return

    RxFirebaseAuth.getCurrentUser(FirebaseAuth.getInstance())
      .flatMapSingle { user -> RxFirebaseUser.getIdToken(user, true) }
      .flatMap { idToken -> AuthorizationAPI.fetchAccessToken(idToken) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        TokenManager.getInstance(applicationContext).setToken(it)
      }, {
        throw it
      })
  }

  companion object {
    const val RC_SIGN_IN = 100
  }
}
