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
//    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.museum_container, MuseumListFragment.newInstance())
        transaction.replace(R.id.author_container, AuthorListFragment.newInstance())
        transaction.replace(R.id.showpiece_container, ShowpieceListFragment.newInstance())
        transaction.commit()
    }


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        mListener = null
//    }
//

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}