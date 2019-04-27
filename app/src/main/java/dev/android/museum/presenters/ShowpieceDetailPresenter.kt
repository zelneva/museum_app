package dev.android.museum.presenters

import android.annotation.SuppressLint
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.db.UserDb
import dev.android.museum.fragment.ShowpieceDetailFragment
import dev.android.museum.model.util.SessionObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShowpieceDetailPresenter(val showpieceDetailFragment: ShowpieceDetailFragment) {

    @SuppressLint("CheckResult")
    fun loadInfoShowpieceDetail(showpieceId: String, lang: String = "ru") {
        museumApiService.getLocaleDataShowpieceById(showpieceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { showpieceDetailFragment.displayShowpieceDetailInfo(it) }
    }


    @SuppressLint("CheckResult")
    fun loadAuthorName(authorId: String, lang: String = "ru") {
        museumApiService.getLocaleDataAuthor(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { showpieceDetailFragment.displayAuthorName(it) }

    }


    @SuppressLint("CheckResult")
    fun addFavorite(showpieceId: String): Boolean {
        if (sessionObject != null) {
            museumApiService.createFavorite(showpieceId, sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            return true
        } else {
            showpieceDetailFragment.alertNullUser()
            return false
        }
    }


    @SuppressLint("CheckResult")
    fun deleteFavorite(showpieceId: String) {
        if (sessionObject?.sessionId != null) {
            museumApiService.deleteFavorite(showpieceId, sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }
}
