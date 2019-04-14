package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Showpiece(

        @SerializedName("id")
        var id: String,

        @SerializedName("exhibition")
        var exhibition: Exhibition,

        @SerializedName("author")
        var author: Author,

        @SerializedName("date")
        var date: Long,

        @SerializedName("genre")
        var genre: String,

        @SerializedName("srcPhoto")
        var srcPhoto: String
)
