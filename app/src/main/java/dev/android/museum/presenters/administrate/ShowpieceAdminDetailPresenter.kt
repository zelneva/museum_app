package dev.android.museum.presenters.administrate

import android.annotation.SuppressLint
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.administrate.ShowpieceDetailAdminFragment
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("CheckResult")
class ShowpieceAdminDetailPresenter(var fragment: ShowpieceDetailAdminFragment) {

    fun loadInfoShowpieceDetail(showpieceId: String, lang: String = "ru") {
        museumApiService.getLocaleDataShowpieceById(showpieceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { fragment.displayShowpieceDetailInfo(it) }
    }


    fun loadAuthorName(authorId: String, lang: String = "ru") {
        museumApiService.getLocaleDataAuthor(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { fragment.displayAuthorName(it) }

    }


    @SuppressLint("SimpleDateFormat")
    fun deleteShowpieceFromExhibition(showpiece: ShowpieceLocaleData) {
        museumApiService.updateExhibitionForShowpiece(showpiece.showpiece.id,
                showpiece.showpiece.exhibition.id, sessionObject!!.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    fragment.openShowpieceList(showpiece.showpiece.exhibition.id)
                }
    }


    fun loadAuthor() {
        val authors = arrayListOf<AuthorLocaleData>()

        museumApiService.getAllAuthors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { authorList -> Observable.fromIterable(authorList) }
                .flatMap { author ->
                    App.museumApiService.getLocaleDataAuthor(author.id.toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe { authorLocaleData ->
                    authors.addAll(authorLocaleData)
                    fragment.loadAuthor(authors)
                }
    }


    fun updateShowpiece(id: String, srcPhoto: MultipartBody.Part, year: String, authorName: String,
                        titleRus: String, descRus: String,
                        titleEng: String, descEng: String,
                        titleGer: String, descGer: String): Boolean {
        museumApiService.updateShowpiece(id, srcPhoto, year, authorName,
                titleRus, descRus, titleEng, descEng, titleGer, descGer, sessionObject!!.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        return true
    }


}