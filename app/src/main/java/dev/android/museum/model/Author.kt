package dev.android.museum.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Author(
        @SerializedName("id")
        var id: UUID,

        @SerializedName("bornAt")
        var bornAt: String,

        @SerializedName("diedAt")
        var diedAt: String? //can be live
): Parcelable
