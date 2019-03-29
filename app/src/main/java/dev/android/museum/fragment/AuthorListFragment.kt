package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import dev.android.museum.R
import dev.android.museum.adapter.AuthorRecyclerViewAdapter
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.presenters.AuthorListPresenter


class AuthorListFragment : Fragment() {

    companion object {
        fun newInstance(): AuthorListFragment = AuthorListFragment()
    }

    private lateinit var presenter: AuthorListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: AuthorRecyclerViewAdapter
    lateinit var progressBar: ProgressBar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.author_list_fragment_main, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = AuthorListPresenter(this)
        presenter.loadListAuthor()
        return view
    }

    fun displayAuthors(authorsResponce: ArrayList<AuthorLocaleData>) {
        adapter = AuthorRecyclerViewAdapter(authorsResponce, this.context!!)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun setupView(view: View){
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv = view.findViewById(R.id.author_list_rv)
        rv.layoutManager = llm
    }

}
