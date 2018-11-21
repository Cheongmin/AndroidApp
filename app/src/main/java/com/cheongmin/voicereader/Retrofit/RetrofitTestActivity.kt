package com.cheongmin.voicereader.Retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.Retrofit.APIs.TestAPI
import com.cheongmin.voicereader.Retrofit.Models.TestResponseModel
import com.google.gson.GsonBuilder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_retrofit.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class RetrofitTestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        btn_request.setOnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl(TestAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val retrofitService = retrofit.create(TestAPI.TestService::class.java!!)

            val call = retrofitService.apiDemo()
            call.enqueue(object : Callback<TestResponseModel.TestResponse> {
                override fun onResponse(call: Call<TestResponseModel.TestResponse>, response: Response<TestResponseModel.TestResponse>) {
                    if (response.isSuccessful) {
                        val repo = response.body()
                        tv_response.text = repo.toString()
                        Log.d("INFO", "Success")
                    } else {
                        Log.d("INFO", "Not Success")
                    }
                }

                override fun onFailure(call: Call<TestResponseModel.TestResponse>, t: Throwable) {
                    Log.d("FAIL", "Failure")
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}