package com.cheongmin.voicereader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.auth0.android.jwt.JWT
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.network.TokenManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // init retrofit builder without token for request access token
        RetrofitManager.init()

        // debug login
        if (BuildConfig.DEBUG) {
            showLoginActivity()
            return
        }

        val tokenManager = TokenManager.getInstance(applicationContext)
        if (tokenManager.hasRefreshToken()) {
            val refreshToken = tokenManager.getRefreshToken()
            val isExpired = JWT(refreshToken).isExpired(0)
            if (!isExpired) {
                // Fetch new token by refresh token
                tokenManager.refreshToken()
                        .subscribe ({
                            RetrofitManager.initWithToken(it)
                            showMainActivity()
                        }, {
                            throw it
                        })
            } else {
                showLoginActivity()
            }

        } else {
            showLoginActivity()
        }
    }

    private fun showMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun showLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}
