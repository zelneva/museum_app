package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.abstractFragment.IMuseumListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MuseumListPresenter(var fragment: IMuseumListFragment) {


    fun loadMuseumList() {
        museumApiService.getAllMuseum()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ museums ->
                        fragment.progressBar.visibility = View.VISIBLE
                        fragment.displayList(museums)
                },
                        { t: Throwable? ->
                                Log.println(Log.ERROR, "LIST MUSEUMS ERROR: ", t.toString())
                                fragment.progressBar.visibility = View.GONE
                        },
                        {
                            fragment.progressBar.visibility = View.GONE
                        }
                )
    }


    fun createMuseum(name: String, address: String): Boolean {
        if (name.isNotEmpty() && address.isNotEmpty()) {
            museumApiService.createMuseum(name, address, App.sessionObject!!.sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ loadMuseumList() },{
                        Log.e("MUS ADMIN LIST ER:", it.message)
                    })
            return true
        } else return false
    }
}


