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
import dev.android.museum.fragment.AuthorDetailFragment
import dev.android.museum.model.AuthorLocaleData

class AuthorRecyclerViewAdapter() : RecyclerView.Adapter<AuthorRecyclerViewAdapter.ViewHolder>() {

    private var authors = arrayListOf<AuthorLocaleData>()

    lateinit var context: Context

    constructor(authors: ArrayList<AuthorLocaleData>, context: Context) : this() {
        this.authors = authors
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_author_main, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = authors.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Picasso.get()
//                .load(images[position])
//                .into(holder.image)

        holder.name.text = authors[position].name

        holder.itemView.setOnClickListener {
            val fragment = AuthorDetailFragment.newInstance()
            val activity: MainActivity = context as MainActivity

            val ft = activity.supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            ft.replace(R.id.main_container, fragment).addToBackStack(null).commit()

        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.author_item_image)
        var name: TextView = itemView.findViewById(R.id.author_item_name)
    }
}
