package dev.android.museum.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
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
): Parcelable
