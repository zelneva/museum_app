package dev.android.museum

import android.app.Application
import dev.android.museum.api.MuseumApiService
import dev.android.museum.api.RetrofitClient

class App: Application() {

    companion object {
        lateinit var museumApiService: MuseumApiService
    }

    override fun onCreate() {
        super.onCreate()
        museumApiService = RetrofitClient().getClient()
    }
}
