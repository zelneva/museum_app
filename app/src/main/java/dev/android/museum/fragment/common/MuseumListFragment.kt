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
import dev.android.museum.adapter.MuseumRecyclerViewAdapter
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.fragment.abstractFragment.IMuseumListFragment
import dev.android.museum.model.Museum
import dev.android.museum.presenters.common.MuseumListPresenter


class MuseumListFragment : Fragment(), IMuseumListFragment {

    companion object {
        fun newInstance(): MuseumListFragment = MuseumListFragment()
    }

    private lateinit var presenter: MuseumListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: MuseumRecyclerViewAdapter
    override lateinit var progressBar: ProgressBar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_museum_list_main, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = MuseumListPresenter(this)
        presenter.loadMuseumList()
        return view
    }


    override fun displayList(list: ArrayList<Museum>) {
        if (list.size != 0) {
            adapter = MuseumRecyclerViewAdapter(list, this.context!!)
            rv.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }


    private fun setupView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv = view.findViewById(R.id.museum_list_rv)
        rv.adapter = SampleRecycler()
        rv.layoutManager = llm
    }
}
