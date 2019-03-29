package dev.android.museum.presenters

class UserPresenter {

    fun exitFromAccount(): Boolean {
        // удалить сессию
        return true
    }


    fun resetPassword(oldPassword: String, newPassword: String, confirmPassword: String): Boolean {
        // смена пароля
        return true
    }


    fun resetName(newName: String): Boolean {
        // смена имени
        return true
    }
}
