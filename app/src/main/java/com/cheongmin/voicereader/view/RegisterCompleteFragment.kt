package com.cheongmin.voicereader.view


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cheongmin.voicereader.R
import kotlinx.android.synthetic.main.fragment_register_complete.*


class RegisterCompleteFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register_complete, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    tv_title.text = (activity as RegisterActivity)?.name + "님 환영합니다!"
    btn_next.setOnClickListener {
      val intent = Intent(context, MainActivity::class.java)
      startActivity(intent)
      activity?.finish()
    }
  }
}
