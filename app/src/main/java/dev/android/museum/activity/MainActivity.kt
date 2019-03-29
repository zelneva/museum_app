package dev.android.museum.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dev.android.museum.R
import dev.android.museum.fragment.*


class MainActivity() : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener,
        UserFragment.OnFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        if (savedInstanceState == null) {
            openFragment(MainFragment.newInstance())
        }

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
                //если пользователь залоггинен, то UserFragment, иначе LoginFragment
                openFragment(LoginFragment.newInstance())
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
        transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onButtonSignIn() {
        openFragment(UserFragment.newInstance())
    }


    override fun onButtonSignUp() {
        openFragment(UserFragment.newInstance())
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


//    var doubleBackToExitPressedOnce = false
//
//    override fun onBackPressed() {
//        if (!doubleBackToExitPressedOnce && supportFragmentManager.backStackEntryCount == 0) {
//            this.doubleBackToExitPressedOnce = true
//            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show()
//
//            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
//        } else {
//            supportFragmentManager.popBackStack();
//            super.onBackPressed()
//            return
//        }
//    }
}
