package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.activity.MainActivity
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(val activity: MainActivity) {

    @SuppressLint("CheckResult")
    fun isAdmin(sessionObject: SessionObject):Boolean {
        var flag = true
        museumApiService.getUserInfo(sessionObject.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    flag = user.role == "admin"
                }, { t: Throwable? ->
                    run {
                        Log.println(Log.ERROR, "LOGIN FRAGMENT ERRO: ", t.toString())
                    }
                })
        return flag
    }
}