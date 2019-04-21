package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Comment (

        @SerializedName("id")
        var id: UUID,

        @SerializedName("user")
        var user: User,

        @SerializedName("showpiece")
        var showpiece: Showpiece,

        @SerializedName("text")
        var text: String,

        @SerializedName("date")
        var date: Long
)