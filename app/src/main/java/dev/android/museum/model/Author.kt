package dev.android.museum.model

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import java.util.*

data class Author(
        @SerializedName("id")
        var id: UUID,

        @SerializedName("bornAt")
        var bornAt: Long,

        @SerializedName("diedAt")
        var diedAt: Long? //can be live
)
