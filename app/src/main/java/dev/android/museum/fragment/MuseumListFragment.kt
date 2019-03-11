package dev.android.museum.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.R
import dev.android.museum.adapter.MuseumRecyclerViewAdapter


class MuseumListFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null

    var names = arrayListOf<String>()
    var address = arrayListOf<String>()
    var status = arrayListOf<String>()
    var images = arrayListOf<String>()

    fun initList() {

        for (i in 0..7) {
            names.add("Национальный музей культуры")
            address.add("Москва ул. Ленина 1")
            status.add("Открыт")
            images.add("https://media.timeout.com/images/100892205/image.jpg")
        }
    }

    fun initRecyclerView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val rv = view.findViewById<RecyclerView>(R.id.museum_list_rv)
        rv.layoutManager = llm
        rv.adapter = MuseumRecyclerViewAdapter( names, address, status, images, this.context!!)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.museum_list_fragment, container, false)
        initList()
        initRecyclerView(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnFragmentInteractionListener {
        //         TODO: Update argument type and name
        fun messageFromChildFragment(uri: Uri)
    }

    companion object {
        fun newInstance(): MuseumListFragment = MuseumListFragment()
    }

}