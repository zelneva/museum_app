package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class User (

        @SerializedName("id")
        var id: UUID,

        @SerializedName("name")
        var name: String,

        @SerializedName("username")
        var username: String,

        @SerializedName("password")
        var password: String,

        @SerializedName("role")
        var role: String,

        @SerializedName("srcPhoto")
        var srcPhoto: String
)
