package dev.android.museum.adapter.selection

import androidx.recyclerview.selection.ItemKeyProvider
import dev.android.museum.model.ShowpieceLocaleData

class ShowpieceKeyProvider(private val items: List<ShowpieceLocaleData>) : ItemKeyProvider<ShowpieceLocaleData>(ItemKeyProvider.SCOPE_MAPPED) {

    override fun getKey(position: Int) = items.getOrNull(position)

    override fun getPosition(key: ShowpieceLocaleData) = items.indexOf(key)
}

