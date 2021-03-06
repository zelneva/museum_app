package dev.android.museum.fragment.common

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
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.fragment.abstractFragment.IAuthorListFragment
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.presenters.common.AuthorListPresenter


class AuthorListFragment : Fragment(), IAuthorListFragment {

    companion object {
        fun newInstance(): AuthorListFragment = AuthorListFragment()
    }

    private lateinit var presenter: AuthorListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: AuthorRecyclerViewAdapter
    override lateinit var progressBar: ProgressBar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_author_list_main, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = AuthorListPresenter(this)
        presenter.loadListAuthor()
        return view
    }

    override fun displayList(list: ArrayList<AuthorLocaleData>) {
        if (list.size != 0) {
            adapter = AuthorRecyclerViewAdapter(list, this.context!!)
            rv.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


    private fun setupView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv = view.findViewById(R.id.author_list_rv)
        rv.adapter = SampleRecycler()
        rv.layoutManager = llm
    }

}
