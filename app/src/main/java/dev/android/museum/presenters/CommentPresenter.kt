package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.db.UserDb
import dev.android.museum.fragment.CommentListFragment
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentPresenter(val fragment: CommentListFragment) {

    @SuppressLint("CheckResult")
    fun loadCommentList(showpieceId: String){
        museumApiService.getListCommentByShowpiece(showpieceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fragment.displayCommentList(it) }
    }


    @SuppressLint("CheckResult")
    fun createComment(showpieceId: String, text: String){
        if(sessionObject?.sessionId!! != null){
            museumApiService.createComment(showpieceId, text, sessionObject?.sessionId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{fragment.updateList()}
        }else{
            fragment.alertNullUser()
        }

    }
}