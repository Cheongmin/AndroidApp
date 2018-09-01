package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupViewPager()
        setupBottomNavigationView()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolBar)

        supportActionBar?.run {
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    private fun setupViewPager() {
        viewPager?.adapter = object: FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                when(position) {
                    0 -> return MainHomeFragment.getInstance()
                    1 -> return MainLogFragment.getInstance()
                    2 -> return MainSettingFragment.getInstance()
                }

                return MainHomeFragment.getInstance()
            }

            override fun getCount(): Int = 3
        }

        viewPager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).apply {
                    isChecked = true
                    supportActionBar?.title = title
                }
            }
        })
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView?.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when(it.itemId) {
                R.id.navigate_home -> 0
                R.id.navigate_log -> 1
                R.id.navigate_setting -> 2
                else -> 0
            }
            supportActionBar?.title = it.title

            true
        }
    }
}
