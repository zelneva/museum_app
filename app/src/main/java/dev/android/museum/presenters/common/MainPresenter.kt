package dev.android.museum.presenters.common

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.activity.MainActivity
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MainPresenter(val activity: MainActivity) {

    fun isAdmin(sessionObject: SessionObject) {
        museumApiService.getUserInfo(sessionObject.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    if (user.role == "admin") {
                        activity.openAdminFragment()
                    } else activity.openUserFragment()
                }, { t: Throwable? ->
                    Log.println(Log.ERROR, "LOGIN FRAGMENT ERRO: ", t.toString())
                })

    }
}