package com.cheongmin.voicereader.model

import com.google.gson.annotations.SerializedName

data class User(
  @SerializedName("_id")
  var id: String,

  @SerializedName("created_date")
  var createdDate: Long,

  @SerializedName("display_name")
  var displayName: String,

  @SerializedName("email")
  var email: String,

  @SerializedName("fcm_uid")
  var fcmUid: String
)