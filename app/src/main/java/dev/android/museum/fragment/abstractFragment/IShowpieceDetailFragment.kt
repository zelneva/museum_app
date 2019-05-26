package dev.android.museum.fragment.abstractFragment

import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData

interface IShowpieceDetailFragment {

    fun displayDetailInfo(list: ShowpieceLocaleData)
    fun displayAuthorName(author: AuthorLocaleData)
    fun alertNullUser()
    fun loadAuthor(list: ArrayList<AuthorLocaleData>)
    fun openShowpieceList(exhbitionId: String)
    fun isCheckFavorite(id: String)

}