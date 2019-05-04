package dev.android.museum.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import dev.android.museum.R
import dev.android.museum.model.Exhibition
import dev.android.museum.presenters.administrate.ExhibitionAdminListPresenter
import java.text.SimpleDateFormat
import java.util.*

class ExhibitionAdminRVAdapter() : RecyclerView.Adapter<ExhibitionAdminRVAdapter.ViewHolder>() {

    private var exhibitions = listOf<Exhibition>()
    private lateinit var context: Context
    private lateinit var presenter: ExhibitionAdminListPresenter

    constructor(exhibitions: List<Exhibition>, context: Context, presenter: ExhibitionAdminListPresenter): this() {
        this.exhibitions = exhibitions
        this.context = context
        this.presenter = presenter
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_admin_exhibition, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = exhibitions.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = exhibitions[position].name
        holder.startDate.text = SimpleDateFormat("dd/MM/yyyy").format(Date(exhibitions[position].startsAt))
        holder.finishDate.text = SimpleDateFormat("dd/MM/yyyy").format(Date(exhibitions[position].endsAt))

        holder.delete.setOnClickListener {
            presenter.deleteExhibition(exhibitions[position].id, exhibitions[position].museum.id.toString())
        }

        holder.edit.setOnClickListener {
            presenter.updateAlert(exhibitions[position].id)
        }

        holder.itemView.setOnClickListener {

        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.admin_exhibition_title)
        var startDate: TextView = itemView.findViewById(R.id.admin_exhibition_start_day)
        var finishDate: TextView = itemView.findViewById(R.id.admin_exhibition_finish_day)
        var edit: ImageButton = itemView.findViewById(R.id.admin_edit_exhibition_btn)
        var delete: ImageButton = itemView.findViewById(R.id.admin_delete_exhibition_btn)
    }
}