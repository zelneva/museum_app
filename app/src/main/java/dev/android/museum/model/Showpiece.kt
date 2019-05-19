package dev.android.museum.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Showpiece(

        @SerializedName("id")
        var id: String,

        @SerializedName("exhibition")
        var exhibition: Exhibition,

        @SerializedName("author")
        var author: Author,

        @SerializedName("date")
        var date: Long,

        @SerializedName("srcPhoto")
        var srcPhoto: String
): Parcelable