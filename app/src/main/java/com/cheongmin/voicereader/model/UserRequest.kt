package com.cheongmin.voicereader.model

import com.google.gson.annotations.SerializedName

data class UserRequest(
        @SerializedName("display_name")
        var display_name: String
)