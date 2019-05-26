package dev.android.museum.presenters.account

import android.util.Log
import android.view.View
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.account.FavoriteFragment
import dev.android.museum.model.ShowpieceLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoritePresenter(val fragment: FavoriteFragment) {

    fun loadFavorite(){
        val showpieces = arrayListOf<ShowpieceLocaleData>()

        museumApiService.getFavorite(App.sessionObject?.sessionId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { list -> Observable.fromIterable(list) }
                .flatMap { fav ->
                    museumApiService.getLocaleDataShowpieceById(fav.showpiece.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()) }
                .subscribe({ showpieceLocaleData ->
                    showpieces.addAll(showpieceLocaleData.filter { it.language == "ru" })
                    fragment.displayFavorite(showpieces) },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST SHOW ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        { fragment.progressBar.visibility = View.GONE
                        })
    }
}