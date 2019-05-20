package dev.android.museum.fragment.administrate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import dev.android.museum.R
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.adapter.selection.*
import dev.android.museum.model.ShowpieceLocaleData
import dev.android.museum.presenters.common.ShowpieceSelectorPresenter


class ShowpieceSelectorFragment() :  Fragment() {

    companion object {
        val EXHBITION_ID = "exhibitionId"

        fun newInstance(exhibitionId: String): ShowpieceSelectorFragment {
            val fragment = ShowpieceSelectorFragment()
            val bundle = Bundle()
            bundle.putString(EXHBITION_ID, exhibitionId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var presenter: ShowpieceSelectorPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ShowpieceSelectorAdapter
    lateinit var progressBar: ProgressBar
    private var actionMode: ActionMode? = null
    private lateinit var exhibitionId: String
    var listOfIds = listOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            exhibitionId = arguments!!.get(EXHBITION_ID) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater
                .inflate(R.layout.fragment_showpiece_list, container, false)
        setupView(view)
        presenter = ShowpieceSelectorPresenter(this)
        presenter.start()
        return view
    }


    private fun setupView(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        rv = view.findViewById(R.id.showpiece_list)
        rv.adapter = SampleRecycler()
        rv.layoutManager = GridLayoutManager(this.context, 2)
    }


    private fun setupTracker(items: ArrayList<ShowpieceLocaleData>) {
        if (items.size == 0) {
            return
        }

        val tracker = SelectionTracker
                .Builder<ShowpieceLocaleData>(
                        "Id",
                        rv,
                        ShowpieceKeyProvider(items),
                        ShowpieceLookup(rv),
                        StorageStrategy.createParcelableStorage(ShowpieceLocaleData::class.java))
                .withSelectionPredicate(
                        SelectionPredicates.createSelectAnything<ShowpieceLocaleData>()).build()

        adapter.tracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Any>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (tracker.hasSelection() && actionMode == null) {
                    val activity = getActivity() as AppCompatActivity
                    listOfIds = tracker.selection.asIterable().map { it.id.toString() }
                    actionMode = activity.startSupportActionMode(ActionModeController(tracker, presenter, listOfIds, exhibitionId))
                    setSelectedTitle(tracker.selection.size())
                } else if (!tracker.hasSelection()) {
                    actionMode?.finish()
                    closefragment()
                    actionMode = null
                } else {
                    setSelectedTitle(tracker.selection.size())
                }
            }
        })
    }

    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = "Выбрано: $selected"
    }


    fun displayListShowpiece(showpieceResponse: ArrayList<ShowpieceLocaleData>?) {
        if (showpieceResponse != null) {
            adapter = ShowpieceSelectorAdapter(showpieceResponse)
            rv.adapter = adapter
        }
        adapter.notifyDataSetChanged()
        setupTracker(showpieceResponse!!)
    }


    private fun closefragment() {
        activity!!.supportFragmentManager.addOnBackStackChangedListener(getListener())
    }

    private fun getListener(): OnBackStackChangedListener {

        return OnBackStackChangedListener {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val backStackEntryCount = manager.backStackEntryCount
                val fragment = manager.fragments[backStackEntryCount - 1] as ShowpieceListAdminFragment
                fragment.onResume()
            }
        }
    }

}
