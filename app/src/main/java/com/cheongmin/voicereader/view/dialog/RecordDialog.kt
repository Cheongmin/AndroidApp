package com.cheongmin.voicereader.view.dialog

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import com.cheongmin.voicereader.R
import kotlinx.android.synthetic.main.dialog_record.*

class RecordDialog : BottomSheetDialogFragment() {

  lateinit var listener: RecordDialogListener

  private var recorder: MediaRecorder? = null
  private var path: String? = null

  private val fileName: String = "recorded.mp3"

  private var timeSeconds = 0
  private val timeHandler = Handler()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.dialog_record, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    arguments?.apply {
      path = this.getString("path")
    }

    btn_record_action.setOnClickListener {
      val isChecked = (it as ToggleButton).isChecked
      if (isChecked) {
        startRecording()
      } else {
        stopRecording()
      }
    }
  }

  override fun onDestroy() {
    timeHandler.removeCallbacksAndMessages(null)
    super.onDestroy()
  }

  private fun startRecording() {
    recorder = MediaRecorder().apply {
      setAudioSource(MediaRecorder.AudioSource.MIC)
      setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
      setOutputFile("$path/$fileName")
      setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

      prepare()
      start()
    }

    timeHandler.postDelayed(object: Runnable {
      override fun run() {
        timeSeconds += 1

        tv_recorded_message.text = "%02d:%02d".format(timeSeconds/60, timeSeconds%60)
        timeHandler.postDelayed(this, 1000)
      }
    }, 1000)
  }

  private fun stopRecording() {
    recorder?.apply {
      stop()
      release()
    }
    recorder = null

    listener.onSuccessful("$path/$fileName", tv_recorded_message.text.toString())
    this.dismiss()
  }

  override fun onStop() {
    super.onStop()
    recorder?.release()
    recorder = null
  }
}