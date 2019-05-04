package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.MuseumListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MuseumListPresenter(var museumListFragment: MuseumListFragment) {


    fun loadMuseumList() {
        museumApiService.getAllMuseum()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ museums ->
                        museumListFragment.progressBar.visibility = View.VISIBLE
                        museumListFragment.displayListMuseum(museums)
                },
                        { t: Throwable? ->
                                Log.println(Log.ERROR, "LIST MUSEUMS ERROR: ", t.toString())
                                museumListFragment.progressBar.visibility = View.GONE
                        },
                        {
                            museumListFragment.progressBar.visibility = View.GONE
                        }
                )
    }
}


