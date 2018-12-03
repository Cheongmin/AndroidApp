package com.cheongmin.voicereader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.androidhuman.rxfirebase2.auth.RxFirebaseAuth
import com.androidhuman.rxfirebase2.auth.RxFirebaseUser
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.api.UserAPI
import com.cheongmin.voicereader.model.AccessToken
import com.cheongmin.voicereader.model.User
import com.cheongmin.voicereader.model.UserRequest
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.user.UserManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.completable.CompletableAmb
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.HttpException
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

        btn_sign_out.setOnClickListener {
            AuthUI.getInstance().signOut(applicationContext)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != RC_SIGN_IN || resultCode != Activity.RESULT_OK)
            return

        RxFirebaseAuth.getCurrentUser(FirebaseAuth.getInstance())
                .flatMapSingle { user -> RxFirebaseUser.getIdToken(user, true) }
                .flatMap { idToken -> AuthorizationAPI.fetchAccessToken(idToken) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    registerToken(it)
                    navigateMainActivity()
                }, {
                    Log.e("Login subscribe", it.message)

                    if (it is HttpException && it.code() == 404) {
                        registerNewUser(FirebaseAuth.getInstance().currentUser!!)
                                .subscribe({
                                    val userManager = UserManager.getInstance(baseContext)
                                    userManager.id = it.id
                                    userManager.user = it
                                }, {
                                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                                })
                    }
                })
    }

    private fun registerToken(token: AccessToken) {
        TokenManager.getInstance(applicationContext).setToken(token)
    }

    private fun registerNewUser(user: FirebaseUser): Single<User> {
        return Single.create { emitter ->
            val request = UserRequest(user.displayName!!)
            UserAPI.newUser(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        emitter.onSuccess(it)
                    }, {
                        emitter.onError(it)
                    })
        }
    }

    private fun navigateMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    companion object {
        const val RC_SIGN_IN = 100
    }
}
