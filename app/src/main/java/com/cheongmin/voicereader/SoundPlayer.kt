package com.cheongmin.voicereader

import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import kotlinx.android.synthetic.main.layout_sound_player.view.*
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.util.TypedValue
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.Toast


class SoundPlayer
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
  ConstraintLayout(context, attrs, defStyleAttr) {

  var subtitles: String = ""
    set(value) {
      tv_subtitles.text = value
    }

  private var isExpanded = false

  init {
    LayoutInflater.from(context).inflate(R.layout.layout_sound_player, this, true)

    setupControllers()
    setupProgressBar()
  }

  private fun setupProgressBar() {
    sb_time_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val seconds = (progress % 60)
        val minutes = (progress / 60)

        tv_play_time.text = resources.getString(R.string.time_placeholder, minutes, seconds)
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {
      }

      override fun onStopTrackingTouch(seekBar: SeekBar?) {
      }

    })
  }

  private fun setupControllers() {
    btn_toggle_subtitle.setOnClickListener {
      toggleSubtitle()
    }

    btn_zoom_in_subtitle.setOnClickListener {
      tv_subtitles.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_subtitles.textSize + 8)
    }

    btn_zoom_out_subtitle.setOnClickListener {
      tv_subtitles.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_subtitles.textSize - 8)
    }
  }

  private fun toggleSubtitle() {
    isExpanded = !isExpanded

    val constraint = ConstraintSet()
    if (isExpanded) {
      constraint.clone(context, R.layout.layout_sound_player_expanded)
    } else {
      constraint.clone(context, R.layout.layout_sound_player)
    }

    constraint.applyTo(root)
  }
}