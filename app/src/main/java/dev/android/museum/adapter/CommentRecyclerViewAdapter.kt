package dev.android.museum.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dev.android.museum.R
import dev.android.museum.model.Comment
import java.text.SimpleDateFormat
import java.util.*


class CommentRecyclerViewAdapter(): RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var comments = arrayListOf<Comment>()

    constructor(comment: ArrayList<Comment>, context: Context) : this() {
        this.comments = comment
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_comment, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = comments.size


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Picasso.get()
//                .load(comments[position].user.srcPhoto)
//                .into(holder.userPhoto)

        holder.userName.text = comments[position].user.name
        holder.commentText.text = comments[position].text
        val date = SimpleDateFormat("dd/MM/yyyy").format(Date(comments[position].date))
        holder.dateCreatedComment.text = date.toString()

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