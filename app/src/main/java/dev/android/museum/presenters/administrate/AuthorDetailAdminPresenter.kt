package dev.android.museum.presenters.administrate

import android.annotation.SuppressLint
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.administrate.AuthorDetailAdminFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

@SuppressLint("CheckResult")
class AuthorDetailAdminPresenter(var fragment: AuthorDetailAdminFragment) {

    fun loadAuthor(authorId: String, language: String = "ru") {
        museumApiService.getLocaleDataAuthor(authorId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == language }
                .subscribe { fragment.displayAuthorDetailInfo(it) }
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
        museumApiService.deleteAuthor(authorId, sessionObject!!.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

}