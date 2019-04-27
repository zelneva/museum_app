package dev.android.museum.presenters.administrate

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.administrate.MuseumAdminDetailFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MuseumAdminDetailPresenter(val fragment: MuseumAdminDetailFragment) {

    @SuppressLint("CheckResult")
    fun loadInfoMuseum(museumId: String) {
        museumApiService.getMuseumById(museumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { fragment.displayInfo(it) },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "DET ADMIN MUS ERROR: ", t.toString())
                        })
    }


    fun deleteMuseum(museumId: String){
        museumApiService.deleteMuseumById(museumId, sessionObject!!.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }


    fun updateMuseum(museumId: String,name: String, address: String): Boolean{
        if(name.isNotEmpty() && address.isNotEmpty()) {
            museumApiService.updateMuseum(museumId, name, address, sessionObject!!.sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            return true
        }
        else return false
    }
}