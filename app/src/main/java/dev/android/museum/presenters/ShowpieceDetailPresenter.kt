package dev.android.museum.presenters

import android.annotation.SuppressLint
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.ShowpieceDetailFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


@SuppressLint("CheckResult")
class ShowpieceDetailPresenter(val showpieceDetailFragment: ShowpieceDetailFragment) {

    fun loadInfoShowpieceDetail(showpieceId: String, lang: String = "ru") {
        museumApiService.getLocaleDataShowpieceById(showpieceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { showpieceDetailFragment.displayShowpieceDetailInfo(it) }
    }


    fun loadAuthorName(authorId: String, lang: String = "ru") {
        museumApiService.getLocaleDataAuthor(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { showpieceDetailFragment.displayAuthorName(it) }

    }


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


    fun deleteFavorite(showpieceId: String) {
        if (sessionObject?.sessionId != null) {
            museumApiService.deleteFavorite(showpieceId, sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }
}
