package dev.android.museum.activity

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dev.android.museum.App
import dev.android.museum.R
import dev.android.museum.model.Museum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import RVAdapter
import dev.android.museum.MuseumListObject


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callFriendDetails: Call<ArrayList<Museum>> = App.museumApiService.getAllMuseum()
        val progressDialog = ProgressDialog(this, R.style.Base_ThemeOverlay_AppCompat_Dialog)
        progressDialog.max = 100
        with(progressDialog) {
            setMessage("Loading....")
            show()
        }

        callFriendDetails.enqueue(object : Callback<ArrayList<Museum>> {
            override fun onFailure(call: Call<ArrayList<Museum>>, t: Throwable) {
                progressDialog.dismiss()
                call.cancel()
            }

            override fun onResponse(call: Call<ArrayList<Museum>>, response: Response<ArrayList<Museum>>) {
                addDetailsMuseum(response.body()!!)
                progressDialog.dismiss()
            }
        })


    }

    fun addDetailsMuseum(list: List<Museum>){
        val museums = arrayListOf<Museum>()
        for (i in 0 until list.size){
            museums.add(list[i])
        }
        initList(museums)
    }


    fun initList(museums: ArrayList<Museum>){
        val rw = this.findViewById<RecyclerView>(R.id.museum_rv)
        rw.layoutManager = LinearLayoutManager(this)
        rw.adapter = RVAdapter(museums, this)
    }
}


