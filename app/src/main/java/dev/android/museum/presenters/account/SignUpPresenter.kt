package dev.android.museum.presenters.account

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.db.UserDb.Companion.addSessionObjectToDB
import dev.android.museum.fragment.account.SignUpFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpPresenter(val signUpFragment: SignUpFragment) {

    @SuppressLint("CheckResult")
    fun signUp(name: String, username: String, password: String, confirmPassword: String): Boolean {
        if (name.length < 2) {
            signUpFragment.alertSmallName()
            return false
        } else if (username.length < 2) {
            signUpFragment.alertSmallUsername()
            return false
        } else if (password.length < 7) {
            signUpFragment.alertSmallPassword()
            return false
        } else {
//            var session :SessionObject?=null
            museumApiService.registration(username, password, name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({addSessionObjectToDB(it!!, signUpFragment.context!!)
                        signUpFragment.signUp()},
                            { t: Throwable? ->
                        run {
                            Log.println(Log.ERROR, "SIGNUP FRAGMENT:", t.toString())
                        }
                    })
            return true

        }
    }

}
