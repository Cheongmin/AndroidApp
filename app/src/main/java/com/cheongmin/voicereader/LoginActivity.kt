package com.cheongmin.voicereader

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import android.util.Log
import android.widget.Toast
import com.androidhuman.rxfirebase2.auth.RxFirebaseAuth
import com.androidhuman.rxfirebase2.auth.RxFirebaseUser
import com.cheongmin.voicereader.model.*
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.service.AuthorizationService
import com.cheongmin.voicereader.service.UserService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.*
import java.io.File
import java.lang.Exception
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.api.UserAPI

class LoginActivity : AppCompatActivity() {
    private val providers = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(), RC_SIGN_IN)
        }

        btn_sign_out.setOnClickListener {
            AuthUI.getInstance().signOut(this)
        }
    }

    private fun updateUserInfo(accessToken: AccessToken): Single<AccessToken> {
        return Single.create {
            try {
                UserInfo.access_token = accessToken.token
                UserInfo.login = true

                Log.i("updateUserInfo", "onSuccess")

                it.onSuccess(accessToken)

            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            return
        }

        RxFirebaseAuth.getCurrentUser(FirebaseAuth.getInstance())
                .flatMapSingle { user -> RxFirebaseUser.getIdToken(user, true) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("idToken", it.toString())
                }, {
                    throw it
                })
    }

    companion object {
        const val RC_SIGN_IN = 100
    }
}
