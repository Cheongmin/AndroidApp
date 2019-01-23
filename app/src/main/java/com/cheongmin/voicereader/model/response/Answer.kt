package com.cheongmin.voicereader.model.response

import com.google.gson.annotations.SerializedName

data class Answer(
  @SerializedName("_id")
  var id: String,

  @SerializedName("contents")
  var contents: String,

  @SerializedName("created_date")
  var createdDate: Long,

  @SerializedName("question_id")
  var questionId: String,

  @SerializedName("writer_id")
  var writerId: String,

  @SerializedName("writer")
  var writer: User
)