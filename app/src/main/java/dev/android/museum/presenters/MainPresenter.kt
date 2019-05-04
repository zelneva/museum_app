package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.activity.MainActivity
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MainPresenter(val activity: MainActivity) {

    fun isAdmin(sessionObject: SessionObject):Boolean {
        var flag = true
        museumApiService.getUserInfo(sessionObject.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    flag = user.role == "admin"
                }, { t: Throwable? ->
                        Log.println(Log.ERROR, "LOGIN FRAGMENT ERRO: ", t.toString())
                })
        return flag
    }
}