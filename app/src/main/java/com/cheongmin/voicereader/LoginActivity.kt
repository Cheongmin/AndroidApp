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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

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

                it.onSuccess(accessToken)

            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    private fun updateUserProfile(): Single<Photo> {
        val apiClient = RetrofitManager.createWithBearerToken(UserService::class.java, UserInfo.access_token)

        val file = File("/sdcard/DCIM/Camera/IMG_20181127_163346.jpg")
        val photo = MultipartBody.Part.createFormData("photo", file.name, RequestBody.create(MediaType.parse("image/*"), file))

        return apiClient.uploadUserPhoto("5bf95d3cb53fe700018fd517", photo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            return
        }
        
        RxFirebaseAuth.getCurrentUser(FirebaseAuth.getInstance())
                .flatMapSingle { user -> RxFirebaseUser.getIdToken(user, true) }
                .flatMap { token ->
                    val apiClient = RetrofitManager.createWithToken(AuthorizationService::class.java, token)
                    return@flatMap apiClient.fetchAccessToken()
                }
                .flatMap { accessToken -> updateUserInfo(accessToken) }
                .flatMap { updateUserProfile() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i(it.uri, "Complete")
                }, {
                    it.printStackTrace()
                })
    }

    companion object {
        const val RC_SIGN_IN = 100
    }
}
