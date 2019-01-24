package com.cheongmin.voicereader.view

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import kotlinx.android.synthetic.main.layout_sound_player.view.*
import android.util.TypedValue
import android.widget.Toast
import com.cheongmin.voicereader.R


class SoundPlayer
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
  ConstraintLayout(context, attrs, defStyleAttr) {

  var subtitles: String = ""
    set(value) {
      tv_subtitles.text = value
    }

  var uri: String = ""
    set(value) {
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
      mediaPlayer.setDataSource(value)
      isPrepared = false
    }

  private var mediaPlayer: MediaPlayer = MediaPlayer()

  private var isPrepared = false
  private var isPreparing = false

  private var isExpanded = false

  private val seekBarUpdateHandler: Handler = Handler()

  private val updateSeekBar: Runnable = object: Runnable {
    override fun run() {
      sb_time_bar.progress = mediaPlayer.currentPosition
      seekBarUpdateHandler.postDelayed(this, 100)
    }
  }

  init {
    LayoutInflater.from(context).inflate(R.layout.layout_sound_player, this, true)

    setupControllers()
    setupProgressBar()
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    seekBarUpdateHandler.removeCallbacks(updateSeekBar)
    mediaPlayer.release()
  }

  private fun setupProgressBar() {
    sb_time_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val time = progress / 1000

        val seconds = (time % 60)
        val minutes = (time / 60)

        tv_play_time.text = resources.getString(R.string.time_placeholder, minutes, seconds)

        if(fromUser) {
          mediaPlayer.seekTo(progress)
        }
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {
      }

      override fun onStopTrackingTouch(seekBar: SeekBar?) {
      }
    })
  }

  private fun setupControllers() {
    btn_toggle_play.setOnClickListener {
      togglePlayAndStop()
    }

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

  private fun togglePlayAndStop() {
    if(isPreparing)
      return

    if(isPrepared) {
      val isPlaying = mediaPlayer.isPlaying
      if(isPlaying) {
        mediaPlayer.pause()
      } else {
        mediaPlayer.start()
      }
    } else {
      isPreparing = true
      mediaPlayer.prepareAsync()

      mediaPlayer.setOnPreparedListener {
        isPreparing = false
        isPrepared = true

        mediaPlayer.start()
        sb_time_bar.max = it.duration
        seekBarUpdateHandler.post(updateSeekBar)
      }

      mediaPlayer.setOnCompletionListener {
        seekBarUpdateHandler.removeCallbacks(updateSeekBar)
      }
    }
  }
}