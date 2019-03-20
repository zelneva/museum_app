package dev.android.museum.fragment

import android.content.Context
import android.net.Uri
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import dev.android.museum.R


class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.museum_container, MuseumListFragment.newInstance())
        transaction.replace(R.id.author_container, AuthorListFragment.newInstance())
        transaction.replace(R.id.showpiece_container, ShowpieceListFragment.newInstance())
        transaction.commit()
    }


    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}