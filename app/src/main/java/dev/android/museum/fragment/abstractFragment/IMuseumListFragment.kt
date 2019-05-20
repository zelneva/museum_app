package dev.android.museum.fragment.abstractFragment

import android.widget.ProgressBar
import dev.android.museum.model.Museum

interface IMuseumListFragment {

    fun displayList(list: ArrayList<Museum>)
    var progressBar: ProgressBar
}