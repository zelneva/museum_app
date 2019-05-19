package dev.android.museum.adapter.selection

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import dev.android.museum.model.ShowpieceLocaleData

class ShowpieceLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<ShowpieceLocaleData>() {

    override fun getItemDetails(e: MotionEvent) = recyclerView.findChildViewUnder(e.x, e.y)
            ?.let {
                (recyclerView.getChildViewHolder(it) as ViewHolderWithDetails<ShowpieceLocaleData>).getItemDetail()
            }

}