package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.R
import dev.android.museum.adapter.AuthorRecyclerViewAdapter


class ShowpieceListFragment : Fragment() {

    var names = arrayListOf<String>()
    var images = arrayListOf<String>()

    private fun initList() {
        for (i in 0..7) {
            when (i) {
                0, 2, 4, 6 -> {
                    names.add("Мона Лиза")
                    images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/250px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg")
                }
                1, 3, 5 -> {
                    names.add("Тайная Вечеря")
                    images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/%C3%9Altima_Cena_-_Da_Vinci_5.jpg/350px-%C3%9Altima_Cena_-_Da_Vinci_5.jpg")

                }
            }
        }
    }

    private fun initRecyclerView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val rv = view.findViewById<RecyclerView>(R.id.showpiece_list_rv)
        rv.layoutManager = llm
        rv.adapter = AuthorRecyclerViewAdapter( names, images, this.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.showpiece_list_fragment, container, false)
        initList()
        initRecyclerView(view)
        return view
    }


    companion object {
        fun newInstance(): ShowpieceListFragment = ShowpieceListFragment()
    }
}
