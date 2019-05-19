package dev.android.museum.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Museum (

        @SerializedName("id")
        var id: UUID,

        @SerializedName("name")
        var name: String,

        @SerializedName("address")
        var address: String
): Parcelable