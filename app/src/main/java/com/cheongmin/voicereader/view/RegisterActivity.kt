package com.cheongmin.voicereader.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.R.id.toolbar
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)
    setupViewPager()
  }

  private fun setupViewPager() {
    viewPager.adapter = object: FragmentStatePagerAdapter(supportFragmentManager) {
      val items = arrayOf(
        RegisterPolicyFragment(),
        RegisterAccountFragment(),
        RegisterUserFragment(),
        RegisterCompleteFragment())

      override fun getItem(position: Int): Fragment {
        return items[position]
      }

      override fun getCount(): Int {
        return items.size
      }
    }

    viewPager.currentItem = 0
  }
}
