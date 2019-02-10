package com.cheongmin.voicereader.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


fun Bitmap.toFile(path:String, fileName:String): File {
  val file = File(path, fileName)
  try {
    FileOutputStream(file).use {
      this.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
  } catch (e: IOException) {
    throw e
  }

  return file
}