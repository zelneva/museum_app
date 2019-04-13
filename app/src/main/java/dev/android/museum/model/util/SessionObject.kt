package dev.android.museum.model.util

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SessionObject(
        @SerializedName("userId")
        var userId: String,

        @SerializedName("sessionId")
        var sessionId: String

):Parcelable
