package dev.android.museum.fragment.abstractFragment

import android.widget.ProgressBar
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData

interface IShowpieceListFragment {

    fun displayList(list: ArrayList<ShowpieceLocaleData>)
    fun loadAuthor(list: ArrayList<AuthorLocaleData>)
    var progressBar: ProgressBar

}