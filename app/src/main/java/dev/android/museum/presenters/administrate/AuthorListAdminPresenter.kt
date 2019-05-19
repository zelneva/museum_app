package dev.android.museum.presenters.administrate

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.fragment.administrate.AuthorListAdminFragment
import dev.android.museum.model.AuthorLocaleData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

@SuppressLint("CheckResult")
class AuthorListAdminPresenter(var fragment: AuthorListAdminFragment) {


    fun loadAuthorList() {
        val authors = arrayListOf<AuthorLocaleData>()

        museumApiService.getAllAuthors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { authorList -> Observable.fromIterable(authorList) }
                .flatMap { author ->
                    App.museumApiService.getLocaleDataAuthor(author.id.toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ authorLocaleData ->
                    authors.addAll(authorLocaleData.filter { it.language == "ru" })
                    fragment.progressBar.visibility = View.VISIBLE
                    fragment.displayListAuthor(authors)
                },
                        { t: Throwable? ->
                            Log.println(Log.ERROR, "LIST AUTHOR ERROR: ", t.toString())
                            fragment.progressBar.visibility = View.GONE
                        },
                        {
                            fragment.progressBar.visibility = View.GONE
                        })
    }


    fun createAuthor(srcPhoto: MultipartBody.Part, born: String, dead: String,
                     titleRus: String, descRus: String,
                     titleEng: String, descEng: String,
                     titleGer: String, descGer: String): Boolean {
        return if (born.length <= 4) {
            museumApiService.createAuthor(srcPhoto, born, dead,
                    titleRus, descRus, titleEng, descEng, titleGer,
                    descGer, App.sessionObject!!.sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            true
        } else false
    }

}
