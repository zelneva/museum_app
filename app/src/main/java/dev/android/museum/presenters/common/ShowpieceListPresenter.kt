package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.abstractFragment.IShowpieceListFragment
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

@SuppressLint("CheckResult")
class ShowpieceListPresenter(val fragment: IShowpieceListFragment) {

    fun loadListShowpiece(){
        val showpieces = arrayListOf<ShowpieceLocaleData>()

        museumApiService.getAllShowpiece()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { showpieceList ->
                    Observable.fromIterable(showpieceList) }
                .flatMap { showpiece ->
                    museumApiService.getLocaleDataShowpieceById(showpiece.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()) }
                .subscribe({ showpieceLocaleData ->
                        showpieces.addAll(showpieceLocaleData.filter { it.language == "ru" })
                        fragment.progressBar.visibility = View.VISIBLE
                        fragment.displayList(showpieces) },
                        { t: Throwable? ->
                                Log.println(Log.ERROR, "LIST SHOW ERROR: ", t.toString())
                                fragment.progressBar.visibility = View.GONE
                        },
                        { fragment.progressBar.visibility = View.GONE
                        })

    }


    fun loadShowpicesListByExhbition(exhibitionId: String) {

        museumApiService.getListShowpieceByExhibitionId(exhibitionId)
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
                    fragment.displayList(showpieceLocaleData.filter{ it.language == "ru" } as ArrayList<ShowpieceLocaleData>)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST SHOW IMAGE ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        {
                            fragment.progressBar.visibility = View.GONE
                        })

    }


    fun loadListShowpieceByAuthor(authorId: String){
        museumApiService.getListShowpieceByAuthorId(authorId)
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
                    fragment.displayList(showpieceLocaleData)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST SHOW IMAGE ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        {
                            fragment.progressBar.visibility = View.GONE
                        })
    }


    fun loadListAuthor() {
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


    fun createShowpiece(srcPhoto: MultipartBody.Part, year: String, authorName: String,
                        titleRus: String, descRus: String,
                        titleEng: String, descEng: String,
                        titleGer: String, descGer: String): Boolean {
        return if (year.length <= 4) {
            museumApiService.createShowpiece(srcPhoto, year, authorName,
                    titleRus, descRus, titleEng, descEng,
                    titleGer, descGer, App.sessionObject!!.sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            true
        } else false
    }
}