package dev.android.museum.presenters

import android.util.Log
import android.view.View
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.AuthorListFragment
import dev.android.museum.model.AuthorLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthorListPresenter(var authorListFragment: AuthorListFragment) {

    fun loadListAuthor() {
        val authors = arrayListOf<AuthorLocaleData>()

        museumApiService.getAllAuthors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { authorList -> Observable.fromIterable(authorList) }
                .flatMap { author ->
                    museumApiService.getLocaleDataAuthor(author.id.toString()).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ authorLocaleData ->
                    run {
                        authors.addAll(authorLocaleData)
                        authorListFragment.progressBar.visibility = View.VISIBLE
                    }
                },
                        { t: Throwable? ->
                            run {
                                Log.println(Log.ERROR, "LIST AUTHOR ERROR: ", t.toString())
                                authorListFragment.progressBar.visibility = View.GONE
                            }
                        },
                        {
                            authorListFragment.displayAuthors(authors)
                            authorListFragment.progressBar.visibility = View.GONE
                        })

    }
}