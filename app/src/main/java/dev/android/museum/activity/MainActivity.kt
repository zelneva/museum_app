package dev.android.museum.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import dev.android.museum.App.Companion.sessionObject
import dev.android.museum.R
import dev.android.museum.fragment.account.*
import dev.android.museum.fragment.administrate.*
import dev.android.museum.fragment.common.*
import dev.android.museum.presenters.common.MainPresenter


class MainActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener,
        UserFragment.OnFragmentInteractionListener, AdminActionFragment.OnFragmentInteractionListener, AuthorDetailFragment.OnFragmentInteractionListener,
        ShowpieceDetailFragment.OnFragmentInteractionListener, MuseumDetailAdminFragment.OnFragmentInteractionListener,
        ShowpieceDetailAdminFragment.OnFragmentInteractionListener, ShowpieceListAdminFragment.OnFragmentInteractionListener{

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        if (savedInstanceState == null) {
            openFragment(MainFragment.newInstance())
        }

        presenter = MainPresenter(this)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.navigation_main -> {
                openFragment(MainFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_camera -> {
                openFragment(NullFragment.newInstance("Здесь будет камера"))
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_user -> {
                if (sessionObject == null) {
                    openFragment(LoginFragment.newInstance())
                } else {
                    presenter.isAdmin(sessionObject!!)
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun openFragmentAnimateDownToUp(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun openAdminFragment() {
        openFragment(AdminFragment.newInstance(sessionObject?.userId!!))
    }


    override fun openUserFragment() {
        openFragment(UserFragment.newInstance(sessionObject?.userId!!))
    }


    override fun onButtonSignUp() {
        openFragment(UserFragment.newInstance(sessionObject?.userId!!))
    }


    override fun onButtonCancel() {
        openFragmentAnimateDownToUp(LoginFragment.newInstance())
    }


    override fun onButtonSignUpOpenFragment() {
        openFragmentAnimateDownToUp(SignUpFragment.newInstance())
    }


    override fun onButtonExit() {
        openFragment(LoginFragment.newInstance())
    }


    override fun openListShowpieceByAuthor(authorId: String) {
        openFragment(ShowpieceImageListFragment.newInstanceForAuthor(authorId))
    }


    override fun openAuthorDetailFragment(authorId: String) {
        openFragment(AuthorDetailFragment.newInstance(authorId))
    }


    override fun openCommentFragment(showpieceId: String) {
        openFragment(CommentListFragment.newInstance(showpieceId))
    }


    override fun openMuseumAdminListFragment() {
        openFragment(MuseumListAdminFragment.newInstance())
    }

    override fun openAdminMuseumList() {
        openFragment(MuseumListAdminFragment.newInstance())
    }

    override fun openExhibitionAdminList(museumId: String) {
        openFragment(ExhibitionListAdminFragment.newInstance(museumId))
    }

    override fun openShowpieceList(exhibitionId: String) {
        openFragment(ShowpieceListAdminFragment.newInstance(exhibitionId))
    }

    override fun openShowpieceSelector(exhibitionId: String) {
        openFragment(ShowpieceSelectorFragment.newInstance(exhibitionId))
    }

    override fun openAuthorListAdminFragment() {
        openFragment(AuthorListAdminFragment.newInstance())
    }

    override fun openShowpiecesListAdminFragment() {
        openFragment(ShowpieceListAdminFragment.newInstance())
    }

}
