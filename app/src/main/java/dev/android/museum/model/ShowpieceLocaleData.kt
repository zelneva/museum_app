package dev.android.museum.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
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
) : Parcelable