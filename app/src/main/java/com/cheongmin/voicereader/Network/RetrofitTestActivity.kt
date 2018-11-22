package com.cheongmin.voicereader.Network

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cheongmin.voicereader.Model.User
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.Service.UserService
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitTestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        btn_request.setOnClickListener {
            val apiClient = RetrofitManager.create(UserService::class.java)
            apiClient.newUsers("gamgule")
                    .enqueue(object: Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            Log.d("Retrofit", "call onResponse")
                            Log.d("Retrofit", response.message())
                            if (response.isSuccessful) {
                                tv_response.text = response.body().toString()
                            }
                        }
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.d("Retrofit", "call onFailure")
                        }
                    })
        }
    }
}