package dev.android.museum

import android.app.Application
import android.content.Context
import dev.android.museum.api.MuseumApiService
import dev.android.museum.api.RetrofitClient
import dev.android.museum.db.UserDb
import dev.android.museum.model.util.SessionObject

class App: Application() {

    companion object {
        lateinit var museumApiService: MuseumApiService
        var sessionObject: SessionObject? = null
    }

    override fun onCreate() {
        super.onCreate()
        museumApiService = RetrofitClient().getClient()
    }

    fun loadSession(context: Context){
        sessionObject = UserDb.loadSessionObject(context)
    }
}
