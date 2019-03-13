package dev.android.museum.activity

import android.content.ClipData
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import dev.android.museum.R
import dev.android.museum.fragment.*


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
                val camera:View  = findViewById(R.id.navigation_camera)
                openFragment(NullFragment.newInstance(""))
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_user -> {
                openFragment(NullFragment.newInstance(""))
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

}


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val callFriendDetails: Call<ArrayList<Museum>> = App.museumApiService.getAllMuseum()
//        val progressDialog = ProgressDialog(this, R.style.Base_ThemeOverlay_AppCompat_Dialog)
//        progressDialog.max = 100
//        with(progressDialog) {
//            setMessage("Loading....")
//            show()
//        }
//
//        callFriendDetails.enqueue(object : Callback<ArrayList<Museum>> {
//            override fun onFailure(call: Call<ArrayList<Museum>>, t: Throwable) {
//                progressDialog.dismiss()
//                call.cancel()
//            }
//
//            override fun onResponse(call: Call<ArrayList<Museum>>, response: Response<ArrayList<Museum>>) {
//                addDetailsMuseum(response.body()!!)
//                progressDialog.dismiss()
//            }
//        })
//
//
//    }
//
//    fun addDetailsMuseum(list: List<Museum>){
//        val museums = arrayListOf<Museum>()
//        for (i in 0 until list.size){
//            museums.add(list[i])
//        }
//        initList(museums)
//    }
//
//
//    fun initList(museums: ArrayList<Museum>){
//        val rw = this.findViewById<RecyclerView>(R.id.museum_rv)
//        rw.layoutManager = LinearLayoutManager(this)
//        rw.adapter = RVAdapter(museums, this)
//    }
//}
//
//
