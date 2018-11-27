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
import android.R.attr.id
import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.GetTokenResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.util.Log
import com.cheongmin.voicereader.model.*
import com.cheongmin.voicereader.network.RetrofitManager
import com.cheongmin.voicereader.network.RetrofitTestActivity
import com.cheongmin.voicereader.service.AuthorizationService
import com.cheongmin.voicereader.service.QuestionService
import com.cheongmin.voicereader.service.UserService
import com.google.android.gms.tasks.OnCompleteListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

                                /*val apiClient = RetrofitManager.createWithToken(UserService::class.java, idToken)
                                apiClient.newUsers(UserRequest("test"))
                                        .enqueue(object : Callback<User> {
                                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                                Log.d("newUsers", "call onResponse")
                                                Log.d("newUsers", response.message())
                                                if (response.isSuccessful) {
                                                    val user = response.body()
                                                    UserInfo.id_token=idToken
                                                    UserInfo.display_name=user!!.display_name
                                                }
                                            }

                                            override fun onFailure(call: Call<User>, t: Throwable) {
                                                Log.d("Retrofit", "call onFailure")
                                                t.printStackTrace()
                                            }
                                        })*/

                                val apiClient2 = RetrofitManager.createWithToken(AuthorizationService::class.java, idToken)
                                apiClient2.fetchAccessToken()
                                        .enqueue(object: Callback<AccessToken> {
                                            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                                                Log.d("fetchAccessToken", "call onResponse")
                                                Log.d("fetchAccessToken", response.message())
                                                if (response.isSuccessful) {
                                                    val accessToken = response.body()
                                                    UserInfo.access_token=accessToken!!.access_token
                                                    UserInfo.login=true

                                                    Log.d("fetchAccessToken", UserInfo.access_token)

                                                    val file = File("/storage/emulated/0/DCIM/Camera/1.jpg")
                                                    if(file.exists()) {
                                                        val photo = MultipartBody.Part.createFormData("photo", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))

                                                        val apiClient = RetrofitManager.createWithBearerToken(UserService::class.java, UserInfo.access_token)
                                                        apiClient.uploadUserPhoto("5bf95d3cb53fe700018fd517", photo)
                                                                .enqueue(object : Callback<Void> {
                                                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                                                        Log.d("Retrofit", "call onResponse")
                                                                        Log.d("Retrofit", response.message())
                                                                        Log.d("Retrofit", response.body().toString())
                                                                    }

                                                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                                                        Log.d("Retrofit", "call onFailure")
                                                                        t.printStackTrace()
                                                                    }
                                                                })
                                                    }
                                                }
                                            }

                                            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                                                Log.d("Retrofit", "call onFailure")
                                                t.printStackTrace()
                                            }
                                        })


                                //5bf95d3cb53fe700018fd517


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
