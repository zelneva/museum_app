package dev.android.museum.fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.R
import dev.android.museum.adapter.ExhibitionRecyclerViewAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExhibitionListFragment : Fragment() {

    var titles = arrayListOf<String>()
    var startDates = arrayListOf<String>()
    var finishDates = arrayListOf<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initList() {
        for (i in 0..7) {
            when (i) {
                0, 2, 4, 6 -> {
                    var date = LocalDate.parse("2018-12-31")
                    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
                    var formattedDate = date.format(formatter)
                    titles.add("Выставка Да Винчи")
                    startDates.add(formattedDate)
                    finishDates.add(formattedDate)
                }
                1, 3, 5 -> {
                    var date = LocalDate.parse("2018-12-31")
                    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
                    var formattedDate = date.format(formatter)
                    titles.add("Мир Пикассо")
                    startDates.add(formattedDate)
                    finishDates.add(formattedDate)
                }
            }
        }
    }

    private fun initRecyclerView(view: View) {
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val rv = view.findViewById<RecyclerView>(R.id.exhibition_list_rv)
        rv.layoutManager = llm
        rv.adapter = ExhibitionRecyclerViewAdapter(titles, startDates, finishDates, this.context!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.exhibition_fragment, container, false)
        initList()
        initRecyclerView(view)
        return view
    }


    companion object {
        fun newInstance(): ExhibitionListFragment = ExhibitionListFragment()
    }
}

