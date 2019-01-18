package com.cheongmin.voicereader.model.request

import com.google.gson.annotations.SerializedName

data class QuestionRequest(
  @SerializedName("subtitles")
  var subtitles: String,

  @SerializedName("contents")
  var contents: String
)