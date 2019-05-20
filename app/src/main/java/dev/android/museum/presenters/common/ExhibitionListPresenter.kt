package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.abstractFragment.IExhibitionListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class ExhibitionListPresenter(val fragment: IExhibitionListFragment) {

    fun loadListExhibition(museumId: String) {
        museumApiService.getExhibitionsByMuseumId(museumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ exhibitions ->
                    fragment.progressBar.visibility = View.INVISIBLE
                    fragment.displayListExhbition(exhibitions)
                }, { t: Throwable? ->
                        Log.println(Log.ERROR, "LIST EXHIBITION ERROR: ", t.toString())
                        fragment.progressBar.visibility = View.GONE
                }, {
                    fragment.progressBar.visibility = View.GONE
                })
    }


    fun updateExhibition(exhibitionId: String, title: String, startsAt: String, endsAt: String, museumId: String?): Boolean {
        if (title.isNotEmpty() && startsAt.isNotEmpty() && endsAt.isNotEmpty() && museumId != null) {
            museumApiService.updateExhibition(exhibitionId, title, startsAt, endsAt, museumId, App.sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { loadListExhibition(museumId) }
            return true
        } else return false
    }


    fun updateAlert(exhibitionId: String) {
        museumApiService.getExhibitionById(exhibitionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fragment.editExhbition(it) }
    }


    fun deleteExhibition(exhibitionId: String, museumId: String) {
        museumApiService.deleteExhibitionById(exhibitionId, App.sessionObject?.sessionId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadListExhibition(museumId) }
    }


    fun createExhibition(title: String, start: String, finish: String, museumId: String): Boolean {
        if (title.isNotEmpty() && start.isNotEmpty() && finish.isNotEmpty()) {
            museumApiService.createExhibition(title, start, finish, museumId, App.sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ loadListExhibition(museumId) })
            return true
        } else return false
    }
}
