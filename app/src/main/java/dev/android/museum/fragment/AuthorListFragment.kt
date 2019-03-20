package dev.android.museum.fragment

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import dev.android.museum.R
import dev.android.museum.adapter.AuthorRecyclerViewAdapter


class AuthorListFragment: Fragment() {

    var names = arrayListOf<String>()
    var images = arrayListOf<String>()

    private fun initList() {
        for (i in 0..7) {
            names.add("Винсент Ван Гог")
            images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Autoportrait_de_Vincent_van_Gogh.JPG/210px-Autoportrait_de_Vincent_van_Gogh.JPG")
        }
    }

    private fun initRecyclerView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val rv = view.findViewById<RecyclerView>(R.id.author_list_rv)
        rv.layoutManager = llm
        rv.adapter = AuthorRecyclerViewAdapter( names, images, this.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.author_list_fragment_main, container, false)
        initList()
        initRecyclerView(view)
        return view
    }


    companion object {
        fun newInstance(): AuthorListFragment = AuthorListFragment()
    }
}