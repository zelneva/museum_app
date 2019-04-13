package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.db.UserRepository
import dev.android.museum.fragment.UserFragment
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserPresenter(var userFragment: UserFragment) {

    val sessionObject  = loadSessionObject()
    val sessionId = sessionObject.sessionId

    @SuppressLint("CheckResult")
    fun exitFromAccount(): Boolean {
        museumApiService.logout(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteSessionObject(sessionObject)
                    deleteAllSession()
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
                .subscribe({}, { t: Throwable? ->
                    run {
                        Log.println(Log.ERROR, "USERFRAGMENT ER DEL: ", t.toString())
                    }
                }, { userFragment.exitFromAccount() })
    }


    fun resetPassword(oldPassword: String, newPassword: String, confirmPassword: String): Boolean {
        // смена пароля
        return true
    }


    fun resetName(newName: String): Boolean {
        // смена имени
        return true
    }


    // загрузка данных из sqlite
    private fun loadSessionObject(): SessionObject {
        val list =  UserRepository(userFragment.context!!).findAll()
        for( i in list){
            Log.d("SESSION",i.userId + " " + i.sessionId)
        }
        return list.last()
    }


    //удаление данных из sqlite
    private fun deleteSessionObject(sessionObject: SessionObject) {
        UserRepository(userFragment.context!!).delete(sessionObject)
    }

    private fun deleteAllSession(){
        UserRepository(userFragment.context!!).deleteAll()
    }
}
