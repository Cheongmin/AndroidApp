package com.cheongmin.voicereader.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.model.response.User
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.network.UserManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    iv_back.setOnClickListener {
      finish()
    }

    btn_logout.setOnClickListener {
      TokenManager.getInstance(applicationContext).clear()
      UserManager.user = null

      val intent = Intent(this, LoginActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      startActivity(intent)
    }

    val user = intent.getSerializableExtra("dataSource") as User?
    user?.let {
      setupProfile(it)

       if(UserManager.user?.id == user.id) {
         btn_logout.visibility = View.VISIBLE
       }
    }
  }

  private fun setupProfile(user: User) {
    if(user.profileUri.isNotBlank()) {
      Picasso.get().load(user.profileUri).into(iv_user_profile)
    }

    tv_user_name.text = user.displayName
    tv_user_location.text = user.location
  }
}
