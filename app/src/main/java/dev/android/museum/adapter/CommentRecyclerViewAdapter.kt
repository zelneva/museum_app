package dev.android.museum.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dev.android.museum.R


class CommentRecyclerViewAdapter(): RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {

    var images = arrayListOf<String>()
    var names = arrayListOf<String>()
    var dates = arrayListOf<String>()
    var texts = arrayListOf<String>()


    lateinit var context: Context


    constructor(images: ArrayList<String>, names: ArrayList<String>, dates: ArrayList<String>, texts: ArrayList<String>, context: Context) : this() {
        this.images = images
        this.names = names
        this.dates = dates
        this.texts = texts
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item_fragment, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = names.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
                .load(images[position])
                .into(holder.userPhoto)

        holder.userName.text = names[position]
        holder.commentText.text = texts[position]
        holder.dateCreatedComment.text = dates[position]

        holder.itemView.setOnClickListener {
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userPhoto: CircleImageView = itemView.findViewById(R.id.user_photo)
        var userName: TextView = itemView.findViewById(R.id.user_name)
        var dateCreatedComment: TextView = itemView.findViewById(R.id.comment_date)
        var commentText : TextView = itemView.findViewById(R.id.comment_text)
    }
}