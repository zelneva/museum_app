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
import dev.android.museum.adapter.ExhibitionRecyclerViewAdapter
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.fragment.abstractFragment.IExhibitionListFragment
import dev.android.museum.model.Exhibition
import dev.android.museum.presenters.common.ExhibitionListPresenter

class ExhibitionListFragment : Fragment(), IExhibitionListFragment {

    companion object {
        val MUSEUM_ID = "museumId"

        fun newInstance(museumId: String): ExhibitionListFragment {
            val fragment = ExhibitionListFragment()
            val bundle = Bundle()
            bundle.putString(MUSEUM_ID, museumId)
            fragment.arguments = bundle
            return fragment
        }
    }


    private lateinit var presenter: ExhibitionListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ExhibitionRecyclerViewAdapter
    override lateinit var progressBar: ProgressBar
    private lateinit var museumId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if( arguments != null){
            museumId = arguments!!.get(MUSEUM_ID).toString()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exhibition, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = ExhibitionListPresenter(this)
        presenter.loadListExhibition(museumId)
        return view
    }


    override fun displayListExhbition(list: ArrayList<Exhibition>) {
        if (list.size != 0) {
            adapter = ExhibitionRecyclerViewAdapter(list, this.context!!)
            rv.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }


    private fun setupView(view: View) {
        rv = view.findViewById(R.id.exhibition_list_rv)
        rv.adapter = SampleRecycler()
        rv.layoutManager = LinearLayoutManager(this.context)
    }

    override fun editExhbition(exhibition: Exhibition) {
    }
}

