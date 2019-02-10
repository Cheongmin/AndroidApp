package com.cheongmin.voicereader.model.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
  @SerializedName("display_name")
  var displayName: String,

  @SerializedName("location")
  var location: String
)