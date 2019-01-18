package com.cheongmin.voicereader.view.dialog

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cheongmin.voicereader.R
import kotlinx.android.synthetic.main.dialog_record.*

class RecordDialog : BottomSheetDialogFragment() {

  lateinit var listener: RecordDialogListener

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.dialog_record, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    btn_record_action.setOnClickListener {
      Toast.makeText(context, "Call Record", Toast.LENGTH_SHORT).show()
    }
  }
}