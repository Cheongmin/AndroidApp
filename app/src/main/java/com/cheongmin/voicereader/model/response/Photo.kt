package com.cheongmin.voicereader.model.response

import com.google.gson.annotations.SerializedName

data class Photo(
  @SerializedName("photo_url")
  val uri: String
)