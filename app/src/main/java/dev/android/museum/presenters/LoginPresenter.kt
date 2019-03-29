package dev.android.museum.presenters

class LoginPresenter(var username: String, var password: String) {

    fun authorize(): Boolean {
        //провериь есть ли пользователь в системе с такими username и password
        return true
    }
}