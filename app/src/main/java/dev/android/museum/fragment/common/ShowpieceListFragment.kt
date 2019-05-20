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
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.adapter.ShowpieceRecyclerViewAdapter
import dev.android.museum.fragment.abstractFragment.IShowpieceListFragment
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData
import dev.android.museum.presenters.common.ShowpieceListPresenter


class ShowpieceListFragment : Fragment(), IShowpieceListFragment {

    override lateinit var progressBar: ProgressBar
    private lateinit var presenter: ShowpieceListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ShowpieceRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_showpiece_list_main, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = ShowpieceListPresenter(this)
        presenter.loadListShowpiece()
        return view
    }


    override fun displayList(list: ArrayList<ShowpieceLocaleData>) {
        if(list.size != 0) {
            adapter = ShowpieceRecyclerViewAdapter(list, this.context!!)
            rv.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


    private fun setupView(view: View){
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv = view.findViewById(R.id.showpiece_list_rv)
        rv.adapter = SampleRecycler()
        rv.layoutManager = llm
    }

    override fun loadAuthor(list: ArrayList<AuthorLocaleData>) {
    }

    companion object {
        fun newInstance(): ShowpieceListFragment = ShowpieceListFragment()
    }
}
