package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.R


class AdminFragment : Fragment() {


    companion object {
        fun newInstance() = AdminFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.user_container, UserFragment.newInstance())
        transaction.replace(R.id.admin_container, AdminActionFragment.newInstance())
        transaction.commit()
    }

}
