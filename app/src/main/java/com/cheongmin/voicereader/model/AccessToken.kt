package com.cheongmin.voicereader.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
        @SerializedName("access_token")
        var access_token: String
)