package dev.android.museum.presenters

import android.annotation.SuppressLint
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.db.UserDb
import dev.android.museum.fragment.ShowpieceDetailFragment
import dev.android.museum.model.util.SessionObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShowpieceDetailPresenter(val showpieceDetailFragment: ShowpieceDetailFragment) {

    private val sessionObject: SessionObject? = UserDb.loadSessionObject(showpieceDetailFragment.context!!)
    private val sessionId: String? = sessionObject?.sessionId

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
        if (sessionId != null) {
            museumApiService.createFavorite(showpieceId, sessionId)
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
        if (sessionId != null) {
            museumApiService.deleteFavorite(showpieceId, sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }
}
