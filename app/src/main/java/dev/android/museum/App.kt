package dev.android.museum

import android.app.Application
import android.content.Context
import dev.android.museum.api.MuseumApiService
import dev.android.museum.api.RetrofitClient
import dev.android.museum.db.UserDbHelper

class App: Application() {

    companion object {
        lateinit var museumApiService: MuseumApiService
    }

    override fun onCreate() {
        super.onCreate()
        museumApiService = RetrofitClient().getClient()
    }
}
