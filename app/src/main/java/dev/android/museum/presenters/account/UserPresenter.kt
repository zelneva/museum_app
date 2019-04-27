package dev.android.museum.presenters.account

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.db.UserDb.Companion.delete
import dev.android.museum.db.UserDb.Companion.deleteAllSession
import dev.android.museum.fragment.account.UserFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserPresenter(var userFragment: UserFragment) {

    @SuppressLint("CheckResult")
    fun exitFromAccount(): Boolean {
        museumApiService.logout(sessionObject?.sessionId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
//                    deleteSessionObject(sessionObject, userFragment.context!!)
                    delete(sessionObject!!, userFragment.context!!)
//                    deleteAllSession(appContext!!)
                    userFragment.exitFromAccount()
                    Log.d("SESS", sessionObject!!.sessionId)
                }, { t: Throwable? ->
                    run {
                        Log.println(Log.ERROR, "USER FRAGMENT ER EXIT: ", t.toString())
                    }
                })
        return true
    }


    @SuppressLint("CheckResult")
    fun deleteAccount() {
        museumApiService.deleteUser(sessionObject?.sessionId!!)
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
            museumApiService.getUserInfo(sessionObject?.userId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        museumApiService.updateUser(sessionObject?.userId!!, it.name, it.username, newPassword, sessionObject?.sessionId!!)
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
            museumApiService.getUserInfo(sessionObject?.userId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        museumApiService.updateUser(sessionObject?.userId!!, newName, it.username, it.password, sessionObject?.sessionId!!)
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

    @SuppressLint("CheckResult")
    fun getUserInfo(userId: String){
        museumApiService.getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{userFragment.displayUserInfo(it)}
    }

}
