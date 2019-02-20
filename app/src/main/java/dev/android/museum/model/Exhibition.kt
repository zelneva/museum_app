package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Exhibition (

        @SerializedName("id")
        var id: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("startsAt")
        var startsAt: Date,

        @SerializedName("endsAt")
        var endsAt: Date,

        @SerializedName("museum")
        var museum: Museum
)
