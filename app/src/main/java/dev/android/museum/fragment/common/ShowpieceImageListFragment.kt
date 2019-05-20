package dev.android.museum.fragment.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
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

class ShowpieceImageListFragment : Fragment(), IShowpieceListFragment {

    companion object {
        val EXHIBITION_ID = "exhibitionId"
        val AUTHOR_ID = "authorId"

        fun newInstance(exhibitionId: String): ShowpieceImageListFragment {
            val fragment = ShowpieceImageListFragment()
            val bundle = Bundle()
            bundle.putString(EXHIBITION_ID, exhibitionId)
            fragment.arguments = bundle
            return fragment
        }


        fun newInstanceForAuthor(authorId: String): ShowpieceImageListFragment {
            val fragment = ShowpieceImageListFragment()
            val bundle = Bundle()
            bundle.putString(AUTHOR_ID, authorId)
            fragment.arguments = bundle
            return fragment
        }
    }


    private lateinit var presenter: ShowpieceListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ShowpieceRecyclerViewAdapter
    override lateinit var progressBar: ProgressBar
    private var exhibitionId: String? = null
    private var authorId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            if (arguments!!.get(EXHIBITION_ID) != null) {
                exhibitionId = arguments!!.get(EXHIBITION_ID) as String
            } else if (arguments!!.get(AUTHOR_ID) != null) {
                authorId = arguments!!.get(AUTHOR_ID) as String
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_showpiece_list, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = ShowpieceListPresenter(this)
        if (exhibitionId != null) {
            presenter.loadShowpicesListByExhbition(exhibitionId!!)
        } else if (authorId != null) {
            presenter.loadListShowpieceByAuthor(authorId!!)
        }

        return view
    }


    private fun setupView(view: View) {
        rv = view.findViewById(R.id.showpiece_list)
        rv.adapter = SampleRecycler()
        rv.layoutManager = GridLayoutManager(this.context, 2)
    }


    override fun displayList(list: ArrayList<ShowpieceLocaleData>) {
        if (list.size != 0) {
            adapter = ShowpieceRecyclerViewAdapter(list, this.context!!)
            rv.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }


    override fun loadAuthor(list: ArrayList<AuthorLocaleData>) {
    }
}
