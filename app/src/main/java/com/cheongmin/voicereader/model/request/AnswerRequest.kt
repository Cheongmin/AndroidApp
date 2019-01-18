package com.cheongmin.voicereader.model.request

import com.google.gson.annotations.SerializedName

data class AnswerRequest(
  @SerializedName("contents")
  var contents: String
)