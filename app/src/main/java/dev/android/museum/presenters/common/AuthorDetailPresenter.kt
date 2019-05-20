package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.abstractFragment.IAuthorDetailFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

@SuppressLint("CheckResult")
class AuthorDetailPresenter(val fragment: IAuthorDetailFragment) {

    fun loadInfoAuthorDetail(authorId: String, lang: String = "ru") {
        museumApiService.getLocaleDataAuthor(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { fragment.displayDetailInfo(it) }
    }


    fun loadAuthorConstantData(authorId: String){
        museumApiService.getAuthorById(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {fragment.displayDate(it) }
    }


    fun updateInfoAuthor(authorId: String, srcPhoto: MultipartBody.Part, born: String, dead: String,
                         titleRus: String, descRus: String,
                         titleEng: String, descEng: String,
                         titleGer: String, descGer: String): Boolean {
        return if (born.length <= 4) {
            museumApiService.updateAuthor(authorId, srcPhoto, born, dead,
                    titleRus, descRus, titleEng, descEng, titleGer,
                    descGer, App.sessionObject!!.sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            true
        } else false
    }


    fun deleteAuthor(authorId: String){
        museumApiService.deleteAuthor(authorId, App.sessionObject!!.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }


}
