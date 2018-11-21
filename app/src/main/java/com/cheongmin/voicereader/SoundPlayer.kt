package com.cheongmin.voicereader

import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import kotlinx.android.synthetic.main.layout_sound_player.view.*
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.TypedValue


class SoundPlayer
    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    var subtitles : String = ""
        set(value) {
            tv_subtitles.text = value
        }

    private val expandedSubtitleLayoutHeight : Int =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80.0f, resources.displayMetrics).toInt()

    private var isExpanded = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_sound_player, this, true)

        setupEvents()
        setupProgressBar()
    }

    private fun setupProgressBar() {
        sb_time_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
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

    private fun setupEvents() {
        btn_toggle_subtitle.setOnClickListener {
            toggleSubtitle()
        }
    }

    private fun toggleSubtitle() {
        isExpanded = !isExpanded

        val startValue = sv_subtitles.measuredHeight
        val endValue = if (isExpanded) expandedSubtitleLayoutHeight else 0

        var animator = ValueAnimator.ofInt(startValue, endValue)
        animator.interpolator = FastOutSlowInInterpolator()

        animator.addUpdateListener {
            val params = sv_subtitles.layoutParams
            params.height = it.animatedValue as Int

            sv_subtitles.layoutParams = params
        }

        animator.setDuration(500).start()
    }
}