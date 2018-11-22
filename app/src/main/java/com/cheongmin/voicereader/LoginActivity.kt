package com.cheongmin.voicereader

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.R.attr.data
import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener






class LoginActivity : AppCompatActivity() {
    private val providers = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener(View.OnClickListener {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), RC_SIGN_IN)
        })

        btn_sign_out.setOnClickListener(View.OnClickListener {
            AuthUI.getInstance().signOut(this)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode === Activity.RESULT_OK) {
                // Successfully signed in
                val mUser = FirebaseAuth.getInstance().currentUser
                mUser!!.getIdToken(true)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val idToken = task.result!!.token
                                // Send token to your backend via HTTPS
                                // ...
                                Log.i("gyuhwan", idToken)
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                txt_debug.text = "Failed"
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 123
    }
}
