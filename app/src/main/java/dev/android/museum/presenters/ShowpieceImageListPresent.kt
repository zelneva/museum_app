package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.ShowpieceImageListFragment
import dev.android.museum.model.ShowpieceLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class ShowpieceImageListPresent(val fragment: ShowpieceImageListFragment) {

    fun loadListShowpieceImage(exhibitionId: String) {

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
                    fragment.displayListShowpiece(showpieceLocaleData.filter{ it.language == "ru" } as ArrayList<ShowpieceLocaleData>)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST SHOW IMAGE ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        {
                            fragment.progressBar.visibility = View.GONE
                        })

    }


    fun loadListShowpieceImageForAuthor(authorId: String){
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
                    fragment.displayListShowpiece(showpieceLocaleData)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST SHOW IMAGE ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        {
                            fragment.progressBar.visibility = View.GONE
                        })
    }


}