package com.cheongmin.voicereader.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
  @SerializedName("_id")
  var id: String,

  @SerializedName("created_date")
  var createdDate: Long,

  @SerializedName("display_name")
  var displayName: String,

  @SerializedName("picture")
  var profileUri: String,

  @SerializedName("email")
  var email: String,

  @SerializedName("fcm_uid")
  var fcmUid: String,

  @SerializedName("location")
  var location: String

) : Serializable