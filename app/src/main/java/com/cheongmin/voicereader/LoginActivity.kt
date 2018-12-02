package com.cheongmin.voicereader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.androidhuman.rxfirebase2.auth.RxFirebaseAuth
import com.androidhuman.rxfirebase2.auth.RxFirebaseUser
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.network.TokenManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class LoginActivity : AppCompatActivity() {
    private val providers = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(), RC_SIGN_IN)
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
