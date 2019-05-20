package dev.android.museum.fragment.abstractFragment

import android.widget.ProgressBar
import dev.android.museum.model.AuthorLocaleData

interface IAuthorListFragment {

    fun displayList(list: ArrayList<AuthorLocaleData>)
    var progressBar: ProgressBar
}