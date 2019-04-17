@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import dev.android.museum.R
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.adapter.ShowpieceRecyclerViewAdapter
import dev.android.museum.model.ShowpieceLocaleData
import dev.android.museum.presenters.ShowpieceImageListPresent

class ShowpieceImageListFragment: Fragment() {

    companion object {
        val EXHIBITION_ID = "exhibitionId"

        fun newInstance(exhibitionId: String): ShowpieceImageListFragment {
            val fragment = ShowpieceImageListFragment()
            val bundle = Bundle()
            bundle.putString(EXHIBITION_ID, exhibitionId)
            fragment.arguments = bundle
            return fragment
        }

        //для автора создать отдельную функцию
    }


    private lateinit var presenter: ShowpieceImageListPresent
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ShowpieceRecyclerViewAdapter
    lateinit var progressBar: ProgressBar
    private lateinit var exhibitionId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if( arguments != null){
            exhibitionId = arguments!!.get(EXHIBITION_ID).toString()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_showpiece_list, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = ShowpieceImageListPresent(this)
        presenter.loadListShowpieceImage(exhibitionId)
        return view
    }


    private fun setupView(view: View) {
        rv = view.findViewById(R.id.showpiece_list)
        rv.adapter = SampleRecycler()
        rv.layoutManager = GridLayoutManager(this.context, 2)
    }


    fun displayListShowpiece(showpieceResponse: ArrayList<ShowpieceLocaleData>?) {
        if (showpieceResponse != null) {
            adapter = ShowpieceRecyclerViewAdapter(showpieceResponse, this.context!!)
            rv.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }
}
