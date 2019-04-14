package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.ShowpieceListFragment
import dev.android.museum.model.ShowpieceLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShowpieceListPresenter(val showpieceListFragment: ShowpieceListFragment) {

    @SuppressLint("CheckResult")
    fun loadListShowpiece(){
        val showpieces = arrayListOf<ShowpieceLocaleData>()

        museumApiService.getAllShowpiece()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { showpieceList ->
                    var l = showpieceList
                    Observable.fromIterable(showpieceList) }
                .flatMap { showpiece ->
                    val a = showpiece
                    museumApiService.getLocaleDataShowpieceById(showpiece.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ showpieceLocaleData ->
                    run {
                        val n = showpieceLocaleData
                        showpieces.addAll(showpieceLocaleData)
                        showpieceListFragment.progressBar.visibility = View.VISIBLE
                    }
                },
                        { t: Throwable? ->
                            run {
                                Log.println(Log.ERROR, "LIST SHOW ERROR: ", t.toString())
                                showpieceListFragment.progressBar.visibility = View.GONE
                            }
                        },
                        {
                            showpieceListFragment.displayShowpieces(showpieces)
                            showpieceListFragment.progressBar.visibility = View.GONE
                        })

    }
}