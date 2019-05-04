package dev.android.museum.presenters.administrate

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.administrate.ExhibitionAdminListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class ExhibitionAdminListPresenter(val fragment: ExhibitionAdminListFragment) {

    fun loadListExhibition(museumId: String) {
        museumApiService.getExhibitionsByMuseumId(museumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ exhibitions ->
                    fragment.progressBar.visibility = View.VISIBLE
                    fragment.displayListExhibition(exhibitions)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST EXH ADM ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        { fragment.progressBar.visibility = View.GONE }
                )
    }


    fun updateExhibition(exhibitionId: String, title: String, startsAt: String, endsAt: String, museumId: String?): Boolean {
        if (title.isNotEmpty() && startsAt.isNotEmpty() && endsAt.isNotEmpty() && museumId != null) {
            museumApiService.updateExhibition(exhibitionId, title, startsAt, endsAt, museumId, sessionObject?.sessionId!!)
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
                .subscribe { fragment.alertEditExhibition(it) }
    }


    fun deleteExhibition(exhibitionId: String, museumId: String) {
        museumApiService.deleteExhibitionById(exhibitionId, sessionObject?.sessionId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadListExhibition(museumId) }
    }


    fun createExhibition(title: String, start: String, finish: String, museumId: String): Boolean {
        if (title.isNotEmpty() && start.isNotEmpty() && finish.isNotEmpty()) {
            museumApiService.createExhibition(title, start, finish, museumId, sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ loadListExhibition(museumId) })
            return true
        } else return false
    }
}