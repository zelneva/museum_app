package dev.android.museum.adapter.selection

import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import dev.android.museum.model.ShowpieceLocaleData

class ShowpieceDetails(private val adapterPosition: Int, private val selectedKey: ShowpieceLocaleData?) :
        ItemDetails<ShowpieceLocaleData>() {

    override fun getSelectionKey() = selectedKey

    override fun getPosition() = adapterPosition

}