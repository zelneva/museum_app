package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.administrate.ShowpieceSelectorFragment
import dev.android.museum.model.ShowpieceLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class ShowpieceSelectorPresenter(var fragment: ShowpieceSelectorFragment) {

    fun start() {
        museumApiService.getAllShowpiece()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { showpieceList -> Observable.fromIterable(showpieceList) }
                .flatMap { showpiece ->
                    museumApiService.getLocaleDataShowpieceById(showpiece.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ showpieceLocaleData ->
                    fragment.progressBar.visibility = View.VISIBLE
                    fragment.displayListShowpiece(showpieceLocaleData.filter { it.language == "ru" }
                            as ArrayList<ShowpieceLocaleData>)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST SHOW ADMIN BY EXH:", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        {
                            fragment.progressBar.visibility = View.GONE
                        })
    }


    fun addShowpieces(list: List<String?>, exhibitionId: String) {
        var array = arrayOfNulls<String>(list.size)

        var i = 0
        for (l in list){
            array[i] = l
            i++
        }

        museumApiService.addShowpieceToExhibition(array,
                exhibitionId, sessionObject!!.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}