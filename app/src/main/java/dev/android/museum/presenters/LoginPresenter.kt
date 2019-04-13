package dev.android.museum.presenters

import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.db.UserRepository
import dev.android.museum.fragment.LoginFragment
import dev.android.museum.model.util.LoginObject
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenter(var loginFragment: LoginFragment) {

    fun authorize(username: String, password: String) {
        //проверить есть ли пользователь в системе с такими username и password
        museumApiService.login(LoginObject(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sessionObj ->
                    isAdmin(sessionObj)
                    addSessionObjectToDB(sessionObj)
                    //положить SessionObject в Sqlite и при каждом вход запрашивать актуальные данные
                }, { t: Throwable? ->
                    run {
                        loginFragment.authorize(false)
                        Log.println(Log.ERROR, "LOGIN FRAGMENT ERROR: ", t.toString())
                    }
                }, { loginFragment.authorize(true) })

    }


    //если роль пользователя - "admin", то открываем AdminFragment, иначе UserFragment
    fun isAdmin(sessionObject: SessionObject) {
        museumApiService.isAdmin(sessionObject.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isAdministrator ->
                    if (isAdministrator) {
                        loginFragment.openAdminFragment()
                    } else loginFragment.openUserFragment()
                }, { t: Throwable? ->
                    run {
                        Log.println(Log.ERROR, "LOGIN FRAGMENT ERROR: ", t.toString())
                    }
                })
    }


    // Положим данные SessionObject о пользователе в sqlite
    private fun addSessionObjectToDB(sessionObject: SessionObject) {
        createSessionObject(sessionObject)
    }


    private fun createSessionObject(sessionObject: SessionObject) {
        val newSessionObject = SessionObject(sessionObject.userId, sessionObject.sessionId)
        UserRepository(loginFragment.context!!).create(newSessionObject)
    }


}