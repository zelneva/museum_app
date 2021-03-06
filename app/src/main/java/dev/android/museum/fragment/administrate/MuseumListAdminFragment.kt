package dev.android.museum.fragment.administrate

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import dev.android.museum.R
import dev.android.museum.adapter.MuseumAdminRVAdapter
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.fragment.abstractFragment.IMuseumListFragment
import dev.android.museum.model.Museum
import dev.android.museum.presenters.common.MuseumListPresenter

class MuseumListAdminFragment : Fragment(), IMuseumListFragment {

    companion object {
        fun newInstance(): MuseumListAdminFragment = MuseumListAdminFragment()
    }

    private lateinit var presenter: MuseumListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: MuseumAdminRVAdapter
    override lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_admin_museum_list, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = MuseumListPresenter(this)
        presenter.loadMuseumList()
        return view
    }


    override fun displayList(museumsResponce: ArrayList<Museum>) {
        if (museumsResponce.size != 0) {
            adapter = MuseumAdminRVAdapter(museumsResponce, this.context!!)
            rv.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }


    private fun setupView(view: View) {
        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener(createNewMuseum)

        rv = view.findViewById(R.id.admin_museum_list)
        rv.adapter = SampleRecycler()
        rv.layoutManager = GridLayoutManager(this.context, 2)
    }


    private val createNewMuseum = View.OnClickListener {
        val builder = AlertDialog.Builder(activity)
        val museumView = activity!!.layoutInflater.inflate(R.layout.dialog_museum, null)
        val title = museumView.findViewById<TextInputLayout>(R.id.new_museum_title)
        val address = museumView.findViewById<TextInputLayout>(R.id.new_museum_address)
        builder.setView(museumView)
        builder.setTitle("Создание музея")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val museumAlert = builder.create()
        museumAlert.show()
        museumAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (presenter.createMuseum(title.editText?.text.toString(), address.editText?.text.toString())) {
                museumAlert.cancel()
            }
        }
    }
}