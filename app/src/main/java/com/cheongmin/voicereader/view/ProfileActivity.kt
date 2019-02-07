package com.cheongmin.voicereader.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.model.response.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    val user = intent.getSerializableExtra("dataSource") as User?
    user?.let {
      setupProfile(it)
    }
  }

  fun setupProfile(user: User) {
    if(user.profileUri.isNotBlank()) {
      Picasso.get().load(user.profileUri).into(iv_user_profile)
    }

    tv_user_name.text = user.displayName
    tv_user_location.text = user.location
  }
}
