package dev.android.museum.adapter.selection

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.SelectionTracker
import dev.android.museum.R
import dev.android.museum.model.ShowpieceLocaleData

class ShowpieceSelectorAdapter(var showpieces: ArrayList<ShowpieceLocaleData>): RecyclerView.Adapter<ShowpieceSelectorAdapter.ViewHolder>(){

    lateinit var tracker: SelectionTracker<ShowpieceLocaleData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_admin_showpiece, parent, false), showpieces
    )

    override fun getItemCount() = showpieces.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit


    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        holder.setActivatedState(tracker.isSelected(showpieces[position]))

        if (SelectionTracker.SELECTION_CHANGED_MARKER !in payloads) {
            holder.title.text = showpieces[position].name
        }

    }

    override fun getItemId(position: Int): Long {
        super.getItemId(position)
        return showpieces[position].id.leastSignificantBits
    }


    class ViewHolder(itemView: View, private val items: List<ShowpieceLocaleData>): RecyclerView.ViewHolder(itemView), ViewHolderWithDetails<ShowpieceLocaleData> {
        var image: ImageView = itemView.findViewById(R.id.showpiece_admin_item_image)
        var title: TextView = itemView.findViewById(R.id.showpiece_admin_item_name)

        override fun getItemDetail() = ShowpieceDetails(adapterPosition, items.getOrNull(adapterPosition))

        fun setActivatedState(isActivated: Boolean) {
            itemView.isActivated = isActivated
        }


    }
}