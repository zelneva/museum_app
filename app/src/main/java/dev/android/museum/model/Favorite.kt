package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Favorite (

        @SerializedName("id")
        var id: UUID,

        @SerializedName("user")
        var user: User,

        @SerializedName("showpiece")
        var showpiece: Showpiece
)