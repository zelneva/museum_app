package dev.android.museum.fragment.abstractFragment

import android.widget.ProgressBar
import dev.android.museum.model.Exhibition

interface IExhibitionListFragment {

    fun displayListExhbition(list: ArrayList<Exhibition>)
    fun editExhbition(exhibition: Exhibition)

    var progressBar: ProgressBar

}