package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.administrate.MuseumDetailAdminFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MuseumDetailPresenter(val fragment: MuseumDetailAdminFragment) {


    fun loadInfoMuseum(museumId: String) {
        museumApiService.getMuseumById(museumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { fragment.displayDetailInfo(it) },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "DET ADMIN MUS ERROR: ", t.toString())
                        })
    }


    fun deleteMuseum(museumId: String) {
        museumApiService.deleteMuseumById(museumId, sessionObject!!.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fragment.openMuseumList() }
    }


    fun updateMuseum(museumId: String, name: String, address: String): Boolean {
        if (name.isNotEmpty() && address.isNotEmpty()) {
            museumApiService.updateMuseum(museumId, name, address, sessionObject!!.sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { loadInfoMuseum(museumId) }
            return true
        } else return false
    }
}