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

@SuppressLint("CheckResult")
class LoginPresenter(var loginFragment: LoginFragment) {


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
                })

    }


    //если роль пользователя - "admin", то открываем AdminFragment, иначе UserFragment
    fun isAdmin(sessionObject: SessionObject) {
        museumApiService.getUserInfo(sessionObject.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { user ->
                    if (user.role == "admin") {
                        loginFragment.openAdminFragment()
                    } else loginFragment.openUserFragment()
                }
    }
}