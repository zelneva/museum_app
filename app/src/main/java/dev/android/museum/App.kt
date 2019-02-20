package dev.android.museum

import android.app.Application
import dev.android.museum.api.MuseumApi
import dev.android.museum.api.RetrofitClient

class App: Application() {

    companion object {
        lateinit var museumApi: MuseumApi
    }

    override fun onCreate() {
        super.onCreate()
        museumApi = RetrofitClient().getClient()
    }
}
