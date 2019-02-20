package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class ShowpieceLocaleData(
        @SerializedName("id")
        var id: UUID,

        @SerializedName("showpiece")
        var showpiece: Showpiece,

        @SerializedName("language")
        var language: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("description")
        var description: String
)