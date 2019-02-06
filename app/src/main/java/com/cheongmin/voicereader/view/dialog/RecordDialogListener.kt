package com.cheongmin.voicereader.view.dialog

interface RecordDialogListener {
  fun onSuccessful(fileName: String, timeText: String)
  fun onCancel()
}