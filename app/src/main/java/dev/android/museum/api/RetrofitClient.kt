package dev.android.museum.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    val baseURL = "http://192.168.1.6:8080/api/"

    fun getClient(): MuseumApi {
        return provideMuseumApi()
    }

    private fun provideMuseumApi(): MuseumApi {
        return provideRetrofit().create(MuseumApi::class.java)
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original!!.newBuilder()
                            .build()
                    return@addInterceptor chain.proceed(request)
                }
                .addInterceptor(provideHttpLoggingInterceptor())
                .build()
    }


    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }
}
