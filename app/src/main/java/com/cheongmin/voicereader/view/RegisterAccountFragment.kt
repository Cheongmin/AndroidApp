package com.cheongmin.voicereader.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.cheongmin.voicereader.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterAccountFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register_account, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<Button>(R.id.btn_next).setOnClickListener {
      val viewPager = (activity as RegisterActivity).viewPager
      viewPager.currentItem += 1
    }
  }
}
