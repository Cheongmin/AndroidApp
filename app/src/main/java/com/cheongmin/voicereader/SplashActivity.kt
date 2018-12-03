package com.cheongmin.voicereader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.auth0.android.jwt.JWT
import com.cheongmin.voicereader.api.UserAPI
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.user.UserManager
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // init retrofit builder without token for request access token
        RetrofitManager.init()

        val tokenManager = TokenManager.getInstance(applicationContext)
        val userManager = UserManager.getInstance(applicationContext)

        // debug main
        RetrofitManager.initWithToken(tokenManager.getToken())
        // temporary id
        userManager.id = "5c0284ed91cf6200014b3909"

        if(userManager.hasId()) {
            UserAPI.fetchUser(userManager.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        userManager.user = it
                        navigateMainActivity()
                    }, {
                        throw it
                    })
        }

        return

        // debug login
        // if (BuildConfig.DEBUG) {
        //    navigateLoginActivity()
        // return
        // }

        if (tokenManager.hasRefreshToken()) {
            val refreshToken = tokenManager.getRefreshToken()
            val isExpired = JWT(refreshToken).isExpired(0)
            if (!isExpired) {
                // Fetch new token by refresh token
                tokenManager.refreshToken()
                        .subscribe ({
                            RetrofitManager.initWithToken(it)
                            navigateMainActivity()
                        }, {
                            throw it
                        })
            } else {
                navigateLoginActivity()
            }

        } else {
            navigateLoginActivity()
        }
    }

    private fun navigateMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun navigateLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}
