package dev.android.museum.presenters

import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.AuthorListFragment
import dev.android.museum.model.Author
import dev.android.museum.model.AuthorLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthorListPresenter(var authorListFragment: AuthorListFragment) {

    fun loadListAuthor() {
        var authors = arrayListOf<AuthorLocaleData>()
        var a = arrayListOf<Author>()

        museumApiService.getAllAuthors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { authorList -> Observable.fromIterable(authorList) }
                .flatMap { author -> museumApiService.getLocaleDataAuthor(author.id.toString()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) }
                .subscribe({ authorLocaleData -> authors.addAll(authorLocaleData) },
                        { t: Throwable? -> Log.println(Log.ERROR, "LIST AUTHOR ERROR: ", t.toString()) },
                        { authorListFragment.displayAuthors(authors) })

    }
}