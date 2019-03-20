package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.R
import dev.android.museum.adapter.ShowpieceRecyclerViewAdapter

class ShowpiecesListFragment: Fragment() {
    var names = arrayListOf<String>()
    var images = arrayListOf<String>()

    private fun initList() {
        for (i in 0..7) {
            when (i) {
                0, 3, 6 -> {
                    names.add("Мона Лиза")
                    images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/250px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg")
                }
                1, 4,7 -> {
                    names.add("Тайная Вечеря")
                    images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/%C3%9Altima_Cena_-_Da_Vinci_5.jpg/350px-%C3%9Altima_Cena_-_Da_Vinci_5.jpg")
                }
                2,5 ->{
                    names.add("9 вал")
                    images.add("https://sr.gallerix.ru/1016087178/_EX/713080393.jpg")
                }

            }
        }
    }

    private fun initRecyclerView(view: View) {
        val llm = GridLayoutManager(this.context, 2)
        val rv = view.findViewById<RecyclerView>(R.id.showpiece_list)
        rv.setHasFixedSize(true)
        rv.layoutManager = llm
        rv.adapter = ShowpieceRecyclerViewAdapter(R.layout.showpiece_list_item, names, images, this.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.showpiece_list_fragment, container, false)
        initList()
        initRecyclerView(view)
        return view
    }


    companion object {
        fun newInstance(): ShowpiecesListFragment = ShowpiecesListFragment()
    }
}