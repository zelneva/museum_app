package dev.android.museum.adapter.selection

import androidx.recyclerview.selection.ItemDetailsLookup

interface ViewHolderWithDetails<TItem> {
    fun getItemDetail(): ItemDetailsLookup.ItemDetails<TItem>
}