package dev.android.museum.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import dev.android.museum.R
import dev.android.museum.adapter.CommentRecyclerViewAdapter

class CommentListFragment : Fragment() {

    var images = arrayListOf<String>()
    var names = arrayListOf<String>()
    var dates = arrayListOf<String>()
    var texts = arrayListOf<String>()

    lateinit var fab: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var editTextComment: TextView
    lateinit var btnSend: ImageButton
    lateinit var sendLayout: LinearLayout


    private fun initList() {
        for (i in 0..7) {
            when (i) {
                0, 2, 4, 6 -> {
                    images.add("https://img.rl0.ru/f3f70ad4661bcac56b285cb86efdea1e/c615x400/news.rambler.ru/img/2018/05/23142637.159422.8999.jpg")
                    names.add("Гарольд Иванов")
                    dates.add("04.12.2019")
                    texts.add("If you use an image loading library like Picasso or Glide, you need to disable their fade animations to avoid messed up images. ")
                }
                1, 3, 5 -> {
                    images.add("https://cs1.livemaster.ru/storage/b9/a3/31934458e92da63b5ab4cbe1b13v--kartiny-i-panno-korgi.jpg")
                    names.add("Корги Пикассо")
                    dates.add("14.02.2019")
                    texts.add("Using a TransitionDrawable with CircleImageView doesn't work properly and leads to messed up images.")
                }
            }
        }
    }

    private fun initRecyclerView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.comment_list_rv)
        recyclerView.layoutManager = llm
        recyclerView.adapter = CommentRecyclerViewAdapter(images, names, dates, texts, this.context!!)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (fab.visibility == View.VISIBLE) {
                        fab.hide()
                    } else if (sendLayout.visibility == View.VISIBLE) {
                        sendLayout.visibility = View.GONE
                    }
                } else if (dy < 0) {
                    if (fab.visibility != View.VISIBLE) {
                        fab.show()
                    } else if (sendLayout.visibility == View.VISIBLE) {
                        sendLayout.visibility = View.GONE
                    }

                }
            }
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.comment_list_fragment, container, false)
        init(view)
        initList()
        initRecyclerView(view)
        return view
    }


    private fun init(view: View) {
        fab = view.findViewById(R.id.fab)
        sendLayout = view.findViewById(R.id.send)
        editTextComment = view.findViewById(R.id.edit_text_comment)
        btnSend = view.findViewById(R.id.btn_send)

        fab.setOnClickListener (clickListenerSendLayoutVisible)
        btnSend.setOnClickListener(clickListenerSend)
    }


    private val clickListenerSend = View.OnClickListener {
        val text = editTextComment.text
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }


    private val clickListenerSendLayoutVisible = View.OnClickListener {
        // проверять залоггинен ли пользователь
        if (sendLayout.visibility == View.VISIBLE) {
            sendLayout.visibility = View.GONE
        } else {
            sendLayout.visibility = View.VISIBLE
            fab.hide()
        }
    }


    companion object {
        fun newInstance(): CommentListFragment = CommentListFragment()
    }

}