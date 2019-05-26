package dev.android.museum.fragment.account

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.R
import dev.android.museum.presenters.account.UserPresenter


class AdminFragment : Fragment() {


    companion object {
        val USER_ID = "userId"

        fun newInstance(userId: String): AdminFragment {
            val fragment = AdminFragment()
            val bundle = Bundle()
            bundle.putString(USER_ID, userId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var userId: String
    private lateinit var presenter: UserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.get(UserFragment.USER_ID) != null) {
            userId = arguments!!.get(UserFragment.USER_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_admin, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.user_container, UserFragment.newInstance(sessionObject?.userId!!))
        transaction.replace(R.id.admin_container, AdminActionFragment.newInstance())
        transaction.commit()
    }




}
