package dev.android.museum.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import dev.android.museum.R
import dev.android.museum.activity.MainActivity
import dev.android.museum.fragment.ShowpieceDetailFragment
import dev.android.museum.model.ShowpieceLocaleData

class ShowpieceRecyclerViewAdapter(): RecyclerView.Adapter<ShowpieceRecyclerViewAdapter.ViewHolder>(){

    private var showpieces = arrayListOf<ShowpieceLocaleData>()
    lateinit var context: Context


    constructor( showpieces: ArrayList<ShowpieceLocaleData>, context: Context) : this() {
        this.showpieces = showpieces
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_showpiece_main, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = showpieces.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Picasso.get()
//                .load(images[position])
//                .into(holder.image)

        holder.title.text = showpieces[position].name

        holder.itemView.setOnClickListener {
            val fragment = ShowpieceDetailFragment.newInstance(showpieces[position].showpiece.id.toString())
            val activity: MainActivity = context as MainActivity

            val ft = activity.supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            ft.replace(R.id.main_container, fragment).addToBackStack(null).commit()

        }
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var image: ImageView = itemView.findViewById(R.id.showpiece_item_image)
        var title: TextView = itemView.findViewById(R.id.showpiece_item_name)
    }
}