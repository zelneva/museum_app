package dev.android.museum.activity

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import dev.android.museum.R
import dev.android.museum.fragment.MainFragment
import dev.android.museum.fragment.NullFragment
import dev.android.museum.fragment.UserFragment


class MainActivity : AppCompatActivity() {


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
                val camera: View = findViewById(R.id.navigation_camera)
                openFragment(NullFragment.newInstance(""))
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_user -> {
                openFragment(UserFragment.newInstance())
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
