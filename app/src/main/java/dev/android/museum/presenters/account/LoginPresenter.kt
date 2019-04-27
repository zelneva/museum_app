package dev.android.museum.presenters.account

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.db.UserDb.Companion.addSessionObjectToDB
import dev.android.museum.fragment.account.LoginFragment
import dev.android.museum.model.util.LoginObject
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenter(var loginFragment: LoginFragment) {

    @SuppressLint("CheckResult")
    fun authorize(username: String, password: String) {
        museumApiService.login(LoginObject(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sessionObj ->
                    addSessionObjectToDB(sessionObj, loginFragment.context!!)
                    App().loadSession(loginFragment.context!!)
                    isAdmin(sessionObj)
                    loginFragment.authorize(true)
                }, { t: Throwable? ->
                        loginFragment.authorize(false)
                        Log.println(Log.ERROR, "LOGIN FRAGMENT AUT ER: ", t.toString())
                })

    }


    //если роль пользователя - "admin", то открываем AdminFragment, иначе UserFragment
    @SuppressLint("CheckResult")
    fun isAdmin(sessionObject: SessionObject) {
        museumApiService.getUserInfo(sessionObject.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    if (user.role == "admin") {
                        loginFragment.openAdminFragment()
                    } else loginFragment.openUserFragment()
                }, { t: Throwable? ->
                    run {
                        Log.println(Log.ERROR, "LOGIN FRAGMENT ERRO: ", t.toString())
                    }
                })
    }
}