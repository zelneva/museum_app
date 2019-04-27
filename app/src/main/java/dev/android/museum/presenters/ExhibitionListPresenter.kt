package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.ExhibitionListFragment
import dev.android.museum.model.Exhibition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ExhibitionListPresenter(val exhibitionListFragment: ExhibitionListFragment) {

    @SuppressLint("CheckResult")
    fun loadExhibitionListByMuseum(museumId: String) {
        museumApiService.getExhibitionsByMuseumId(museumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ exhibitions ->
                    exhibitionListFragment.progressBar.visibility = View.INVISIBLE
                    exhibitionListFragment.displayListExhibition(exhibitions)
                }, { t: Throwable? ->
                        Log.println(Log.ERROR, "LIST EXHIBITION ERROR: ", t.toString())
                        exhibitionListFragment.progressBar.visibility = View.GONE
                }, {
                    exhibitionListFragment.progressBar.visibility = View.GONE
                })
    }
}