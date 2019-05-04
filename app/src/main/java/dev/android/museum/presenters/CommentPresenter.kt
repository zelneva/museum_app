package dev.android.museum.presenters

import android.annotation.SuppressLint
import dev.android.museum.App.Companion.museumApiService
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.fragment.CommentListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class CommentPresenter(val fragment: CommentListFragment) {

    fun loadCommentList(showpieceId: String){
        museumApiService.getListCommentByShowpiece(showpieceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fragment.displayCommentList(it) }
    }


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