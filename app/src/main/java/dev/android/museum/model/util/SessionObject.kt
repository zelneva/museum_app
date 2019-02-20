package dev.android.museum.model.util

import com.google.gson.annotations.SerializedName

data class SessionObject(
        @SerializedName("sessionId")
        var sessionId: String,

        @SerializedName("userId")
        var userId: String
)