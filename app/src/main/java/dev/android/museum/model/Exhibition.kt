package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Exhibition (

        @SerializedName("id")
        var id: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("startsAt")
        var startsAt: Long,

        @SerializedName("endsAt")
        var endsAt: Long,

        @SerializedName("museum")
        var museum: Museum
)
