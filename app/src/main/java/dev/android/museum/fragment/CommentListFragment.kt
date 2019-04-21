package dev.android.museum.fragment

import android.content.Context
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
import android.view.inputmethod.InputMethodManager
import dev.android.museum.adapter.CommentRecyclerViewAdapter
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.model.Comment
import dev.android.museum.presenters.CommentPresenter


class CommentListFragment : Fragment() {

    companion object {
        val SHOWPIECE_ID = "showpieceId"

        fun newInstance(showpieceId: String): CommentListFragment {
            val fragment = CommentListFragment()
            val bundle = Bundle()
            bundle.putString(SHOWPIECE_ID, showpieceId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextComment: TextView
    private lateinit var btnSend: ImageButton
    private lateinit var sendLayout: LinearLayout
    private lateinit var imm: InputMethodManager

    private lateinit var showpieceId: String

    private lateinit var presenter: CommentPresenter
    private lateinit var adapter: CommentRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.get(SHOWPIECE_ID) != null) {
            showpieceId = arguments!!.get(ShowpieceDetailFragment.SHOWPIECE_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comment_list, container, false)
        imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        init(view)
        presenter = CommentPresenter(this)
        presenter.loadCommentList(showpieceId)
        rvAnimateFab()
        return view
    }


    private fun init(view: View) {
        fab = view.findViewById(R.id.fab)
        sendLayout = view.findViewById(R.id.send)
        editTextComment = view.findViewById(R.id.edit_text_comment)
        btnSend = view.findViewById(R.id.btn_send)

        fab.setOnClickListener (clickListenerSendLayoutVisible)
        btnSend.setOnClickListener(clickListenerSend)

        val llm = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.comment_list_rv)
        recyclerView.layoutManager = llm
        recyclerView.adapter = SampleRecycler()
    }


    fun displayCommentList(commentResponse: ArrayList<Comment>?){
        if(commentResponse != null && commentResponse.size != 0){
            adapter = CommentRecyclerViewAdapter(commentResponse, context!!)
            recyclerView.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }


    private fun rvAnimateFab() {
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


    private val clickListenerSend = View.OnClickListener {
        val text = editTextComment.text
        sendLayout.visibility = View.GONE
        imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        presenter.createComment(showpieceId, text.toString())
        editTextComment.text = null
        fab.show()
    }

    fun updateList(){
        presenter.loadCommentList(showpieceId)
    }


    fun alertNullUser(){
        Toast.makeText(this.context, "Войдите, чтобы написать комментарий", Toast.LENGTH_SHORT).show()
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




}