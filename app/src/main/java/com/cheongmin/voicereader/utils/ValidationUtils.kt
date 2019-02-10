package com.cheongmin.voicereader.utils

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
  this.addTextChangedListener(object: TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      afterTextChanged.invoke(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
  })
}

fun TextInputLayout.validate(validator: (String) -> Boolean, message: String) {
  this.editText?.afterTextChanged {
    this.error = if (validator(it)) null else message
  }
  this.error = if (validator(this.editText?.text.toString())) null else message
}
