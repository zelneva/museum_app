package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.abstractFragment.IShowpieceDetailFragment
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody


@SuppressLint("CheckResult")
class ShowpieceDetailPresenter(val fragment: IShowpieceDetailFragment) {

    fun loadInfoShowpieceDetail(showpieceId: String, lang: String = "ru") {
        museumApiService.getLocaleDataShowpieceById(showpieceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe {
                    checkFavorite(showpieceId)
                    fragment.displayDetailInfo(it)
                }
    }


    fun loadAuthorName(authorId: String, lang: String = "ru") {
        museumApiService.getLocaleDataAuthor(authorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it) }
                .filter { it.language == lang }
                .subscribe { fragment.displayAuthorName(it) }

    }


    fun findDuplicate(showpieceId: String): Boolean {
        if (sessionObject != null) {

            museumApiService.getFavorite(sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.isEmpty()) {
                            addFavorite(showpieceId)
                        }
                    }

            museumApiService.getFavorite(sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { g -> Observable.fromIterable(g) }
                    .subscribe {
                        if (it.showpiece.id != showpieceId) {
                            addFavorite(showpieceId)
                        }
                    }
            return true
        }
        else {
            fragment.alertNullUser()
            return false
        }
    }


    fun addFavorite(showpieceId: String): Boolean {
        if (sessionObject != null) {

            museumApiService.createFavorite(showpieceId, sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { fragment.isCheckFavorite(it.id.toString()) }
            return true
        } else {
            fragment.alertNullUser()
            return false
        }
    }


    fun deleteFavorite(id: String) {
        if (sessionObject?.sessionId != null) {
            museumApiService.deleteFavorite(id, sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }
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


    fun checkFavorite(showpieceId: String) {
        if (sessionObject != null) {
            museumApiService.getFavorite(sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { Observable.fromIterable(it) }
                    .subscribe {
                        if (it.showpiece.id == showpieceId) {
                            fragment.isCheckFavorite(it.id.toString())
                        }
                    }
        }
    }


    fun deleteShowpiece(showpieceId: String){
        //СДЕЛАТЬ!!!
    }

}
