package dev.android.museum.presenters

import android.annotation.SuppressLint
import android.util.Log
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.db.UserDb
import dev.android.museum.fragment.CommentListFragment
import dev.android.museum.model.util.SessionObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentPresenter(val fragment: CommentListFragment) {

    private val sessionObject: SessionObject? = UserDb.loadSessionObject(fragment.context!!)
    private val sessionId: String? = sessionObject?.sessionId

    @SuppressLint("CheckResult")
    fun loadCommentList(showpieceId: String){
        museumApiService.getListCommentByShowpiece(showpieceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fragment.displayCommentList(it) }
    }


    @SuppressLint("CheckResult")
    fun createComment(showpieceId: String, text: String){
        Log.d("111", text)
        if(sessionId != null){
            museumApiService.createComment(showpieceId, text, sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{fragment.updateList()}
        }else{
            fragment.alertNullUser()
        }

    }
}