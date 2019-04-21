package dev.android.museum.presenters

import android.annotation.SuppressLint
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.AuthorDetailFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthorDetailPresenter(val fragment: AuthorDetailFragment) {

    @SuppressLint("CheckResult")
    fun loadInfoAuthorDetail(authorId: String, lang: String = "ru") {
        museumApiService.getLocaleDataAuthor(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { fragment.displayAuthorDetailInfo(it) }
    }


    @SuppressLint("CheckResult")
    fun loadAuthorConstantData(authorId: String){
        museumApiService.getAuthorById(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {fragment.displayDate(it) }
    }

}
