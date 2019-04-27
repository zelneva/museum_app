package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Museum (

        @SerializedName("id")
        var id: UUID,

        @SerializedName("name")
        var name: String,

        @SerializedName("address")
        var address: String
)