package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        bottomNavigationView
                .setOnNavigationItemSelectedListener {
                    when(it.itemId) {
                        R.id.navigate_home -> {
                            replaceFragment(MainHomeFragment.getInstance())
                            return@setOnNavigationItemSelectedListener true
                        }
                        R.id.navigate_log -> {
                            replaceFragment(MainLogFragment.getInstance())
                            return@setOnNavigationItemSelectedListener true
                        }
                        R.id.navigate_setting -> {
                            replaceFragment(MainSettingFragment.getInstance())
                            return@setOnNavigationItemSelectedListener true
                        }
                    }
                    false
                }

        replaceFragment(MainHomeFragment.getInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .run {
                    replace(frameLayout.id, fragment)
                    addToBackStack(null)
                    commit()
                }
    }
}
