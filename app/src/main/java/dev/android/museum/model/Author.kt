package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Author(
        @SerializedName("id")
        var id: UUID,

        @SerializedName("bornAt")
        var bornAt: Date,

        @SerializedName("diedAt")
        var diedAt: Date? //can be live
)
