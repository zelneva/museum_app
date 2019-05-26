package dev.android.museum.fragment.account

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
import dev.android.museum.model.Favorite
import dev.android.museum.model.ShowpieceLocaleData
import dev.android.museum.presenters.account.FavoritePresenter

class FavoriteFragment : Fragment() {

//     lateinit var progressBar: ProgressBar
    private lateinit var rv: RecyclerView
    private lateinit var presenter: FavoritePresenter
    private lateinit var adapter:ShowpieceRecyclerViewAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_showpiece_list_main, container, false)
        presenter = FavoritePresenter(this)
        presenter.loadFavorite()
        setupView(view)
        return view
    }


    private fun setupView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv = view.findViewById(R.id.showpiece_list_rv)
        rv.adapter = SampleRecycler()
        rv.layoutManager = llm
        progressBar = view.findViewById(R.id.progress_bar)
    }


    fun displayFavorite(list: ArrayList<ShowpieceLocaleData>){
            if(list.isNotEmpty()) {
                adapter = ShowpieceRecyclerViewAdapter(list, this.context!!)
                rv.adapter = adapter
                adapter.notifyDataSetChanged()
        }
    }


    companion object {
        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }
}