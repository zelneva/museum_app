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
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
                    fragment.displayList(showpieceLocaleData.filter{ it.language == "ru" } as ArrayList<ShowpieceLocaleData>)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST SHOW IMAGE ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        { fragment.progressBar.visibility = View.GONE })
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
            val y = RequestBody.create(MediaType.parse("text/plain"), year)
            val a = RequestBody.create(MediaType.parse("text/plain"), authorName)
            val tr = RequestBody.create(MediaType.parse("text/plain"), titleRus)
            val dr = RequestBody.create(MediaType.parse("text/plain"), descRus)
            val te = RequestBody.create(MediaType.parse("text/plain"), titleEng)
            val de = RequestBody.create(MediaType.parse("text/plain"), descEng)
            val tg = RequestBody.create(MediaType.parse("text/plain"), titleGer)
            val dg = RequestBody.create(MediaType.parse("text/plain"), descGer)
            val s = RequestBody.create(MediaType.parse("text/plain"), App.sessionObject!!.sessionId)
            museumApiService.createShowpiece(srcPhoto,y,a,tr,dr,te,de, tg,dg,s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            true
        } else false
    }
}