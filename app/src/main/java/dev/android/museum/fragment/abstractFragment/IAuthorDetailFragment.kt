package dev.android.museum.fragment.abstractFragment

import dev.android.museum.model.Author
import dev.android.museum.model.AuthorLocaleData

interface IAuthorDetailFragment {

    fun displayDetailInfo(authorLocaleData: AuthorLocaleData)
    fun displayDate(author: Author)
}