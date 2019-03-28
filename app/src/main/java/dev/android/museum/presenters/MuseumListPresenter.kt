package dev.android.museum.presenters

import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.MuseumListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MuseumListPresenter(var museumListFragment: MuseumListFragment) {

    fun loadMuseumList() {
        museumApiService.getAllMuseum()
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ museums ->
                    run {
                        museumListFragment.progressBar.visibility = View.VISIBLE
                        museumListFragment.displayListMuseum(museums)
                    }
                },
                        { t: Throwable? ->
                            run {
                                Log.println(Log.ERROR, "LIST MUSEUMS ERROR: ", t.toString())
                                museumListFragment.progressBar.visibility = View.GONE
                            }
                        },
                        {
                            museumListFragment.progressBar.visibility = View.GONE
                        }
                )
    }
}


