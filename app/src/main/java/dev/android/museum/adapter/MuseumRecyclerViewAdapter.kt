package dev.android.museum.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import dev.android.museum.R
import dev.android.museum.activity.MainActivity
import dev.android.museum.fragment.ExhibitionListFragment
import dev.android.museum.model.Museum

class MuseumRecyclerViewAdapter() : RecyclerView.Adapter<MuseumRecyclerViewAdapter.ViewHolder>() {

    private var museums = listOf<Museum>()
    private lateinit var context: Context


    constructor(museums: List<Museum>, context: Context) : this() {
        this.museums = museums
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_museum_main, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = museums.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Picasso.get()
//                .load(images[position])
//                .into(holder.image)

        holder.name.text = museums[position].name
        holder.address.text = museums[position].address

        holder.itemView.setOnClickListener {

            val activity: MainActivity = context as MainActivity

            val ft = activity.supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            ft.replace(R.id.main_container, ExhibitionListFragment.newInstance(museums[position].id.toString()))
                    .addToBackStack(null)
                    .commit()
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.museum_image)
        var name: TextView = itemView.findViewById(R.id.museum_name)
        var address: TextView = itemView.findViewById(R.id.museum_address)
    }
}
