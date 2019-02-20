package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class AuthorLocaleData(

        @SerializedName("id")
        var id: UUID,

        @SerializedName("author")
        var author: Author,

        @SerializedName("language")
        var language: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("description")
        var description: String
)
