package dev.android.museum.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.android.museum.R
import dev.android.museum.activity.MainActivity
import dev.android.museum.fragment.NullFragment
import dev.android.museum.fragment.ShowpiecesListFragment
import java.util.*

class ExhibitionRecyclerViewAdapter() : RecyclerView.Adapter<ExhibitionRecyclerViewAdapter.ViewHolder>() {

    var titles = arrayListOf<String>()
    var startDates = arrayListOf<String>()
    var finishDates = arrayListOf<String>()

    lateinit var context: Context


    constructor(titles: ArrayList<String>, startDates: ArrayList<String>, finishDates: ArrayList<String>, context: Context) : this() {
        this.titles = titles
        this.startDates = startDates
        this.finishDates = finishDates
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exhibition_list_item, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = titles.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = titles[position]
        holder.startDate.text = startDates[position]
        holder.finishDate.text = finishDates[position]

        holder.itemView.setOnClickListener {
            val showpieceFragment = ShowpiecesListFragment.newInstance()
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