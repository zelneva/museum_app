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

@SuppressLint("CheckResult")
class ShowpieceListPresenter(val showpieceListFragment: ShowpieceListFragment) {

    fun loadListShowpiece(){
        val showpieces = arrayListOf<ShowpieceLocaleData>()

        museumApiService.getAllShowpiece()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { showpieceList ->
                    Observable.fromIterable(showpieceList) }
                .flatMap { showpiece ->
                    museumApiService.getLocaleDataShowpieceById(showpiece.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()) }
                .subscribe({ showpieceLocaleData ->
                        showpieces.addAll(showpieceLocaleData.filter { it.language == "ru" })
                        showpieceListFragment.progressBar.visibility = View.VISIBLE
                        showpieceListFragment.displayShowpieces(showpieces) },
                        { t: Throwable? ->
                                Log.println(Log.ERROR, "LIST SHOW ERROR: ", t.toString())
                                showpieceListFragment.progressBar.visibility = View.GONE
                        },
                        { showpieceListFragment.progressBar.visibility = View.GONE
                        })

    }
}