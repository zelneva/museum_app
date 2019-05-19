package dev.android.museum.presenters.administrate

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.administrate.MuseumListAdminFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


@SuppressLint("CheckResult")
class MuseumAdminListPresenter(val fragment: MuseumListAdminFragment) {

    fun loadMuseumList() {
        museumApiService.getAllMuseum()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ museums ->
                    fragment.progressBar.visibility = View.VISIBLE
                    fragment.displayListMuseum(museums)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST ADMIN MUS ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        { fragment.progressBar.visibility = View.GONE }
                )
    }


    fun createMuseum(name: String, address: String): Boolean {
        if (name.isNotEmpty() && address.isNotEmpty()) {
            museumApiService.createMuseum(name, address, sessionObject!!.sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ fragment.updateList() },{
                        Log.e("MUS ADMIN LIST ER:", it.message)
                    })
            return true
        } else return false
    }
}