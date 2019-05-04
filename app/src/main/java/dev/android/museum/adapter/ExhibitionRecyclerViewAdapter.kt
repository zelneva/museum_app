package dev.android.museum.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.android.museum.R
import dev.android.museum.activity.MainActivity
import dev.android.museum.fragment.ShowpieceImageListFragment
import dev.android.museum.model.Exhibition
import java.text.SimpleDateFormat
import java.util.*

class ExhibitionRecyclerViewAdapter() : RecyclerView.Adapter<ExhibitionRecyclerViewAdapter.ViewHolder>() {

    private var exhibitions = arrayListOf<Exhibition>()
    lateinit var context: Context


    constructor(exhibitions: ArrayList<Exhibition>, context: Context) : this() {
        this.exhibitions = exhibitions
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_exhibition, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount():Int {
        return exhibitions.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = exhibitions[position].name
        holder.startDate.text = SimpleDateFormat("dd/MM/yyyy").format(Date(exhibitions[position].startsAt))
        holder.finishDate.text = SimpleDateFormat("dd/MM/yyyy").format(Date(exhibitions[position].endsAt))

        holder.itemView.setOnClickListener {
            val showpieceFragment = ShowpieceImageListFragment.newInstance(exhibitions[position].id)
            val activity: MainActivity = context as MainActivity

            val ft = activity.supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            ft.replace(R.id.main_container, showpieceFragment).addToBackStack(null).commit()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.exhibition_title)
        var startDate: TextView = itemView.findViewById(R.id.exhibition_start_day)
        var finishDate: TextView = itemView.findViewById(R.id.exhibition_finish_day)
    }
}