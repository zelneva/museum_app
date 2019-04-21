package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.db.UserDb.Companion.deleteAllSession
import dev.android.museum.db.UserDb.Companion.deleteSessionObject
import dev.android.museum.db.UserDb.Companion.loadSessionObject
import dev.android.museum.fragment.UserFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserPresenter(var userFragment: UserFragment) {

    private val sessionObject = loadSessionObject(userFragment.context!!)
    private val sessionId = sessionObject?.sessionId!!
    private val userId = sessionObject?.userId!!

    @SuppressLint("CheckResult")
    fun exitFromAccount(): Boolean {
        museumApiService.logout(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
//                    deleteSessionObject(sessionObject, userFragment.context!!)
                    deleteAllSession(userFragment.context!!)
                }, { t: Throwable? ->
                    run {
                        Log.println(Log.ERROR, "USER FRAGMENT ER EXIT: ", t.toString())
                    }
                }, {
                    userFragment.exitFromAccount()
                })
        return true
    }


    @SuppressLint("CheckResult")
    fun deleteAccount() {
        museumApiService.deleteUser(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteAllSession(userFragment.context!!)
                }, { t: Throwable? ->
                    run {
                        Log.println(Log.ERROR, "USERFRAGMENT ER DEL: ", t.toString())
                    }
                }, { userFragment.exitFromAccount() })
    }


    @SuppressLint("CheckResult")
    fun resetPassword(oldPassword: String, newPassword: String, confirmPassword: String): Boolean {

        if (newPassword.length < 8) {
            userFragment.openAlertDialog("Пароль должен содержать не менее 8 символов")
            return false
        } else if (oldPassword == newPassword) {
            userFragment.openAlertDialog("Старый пароль не должен совпадать с новым")
            return false
        } else if (newPassword != confirmPassword) {
            userFragment.openAlertDialog("Новый пароль и подтвержденный пароль не совпадают")
            return false
        } else {
            museumApiService.getUserInfo(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        museumApiService.updateUser(userId, it.name, it.username, newPassword, sessionId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()
                    }, { t: Throwable? ->
                        run {
                            Log.println(Log.ERROR, "USER FRAGMENT PASSWORD:", t.toString())
                        }
                    })
            return true
        }
    }


    @SuppressLint("CheckResult")
    fun resetName(newName: String): Boolean {
        if (newName.length > 2) {
            museumApiService.getUserInfo(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        museumApiService.updateUser(userId, newName, it.username, it.password, sessionId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()
                    }, { t: Throwable? ->
                        run {
                            Log.println(Log.ERROR, "USER FRAGMENT NEW NAME:", t.toString())
                        }
                    })
        }
        return true
    }

}
