package com.example.covidstatus.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging).build()
            Retrofit.Builder().baseUrl("https://api.rootnet.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }
        val api by lazy {
            retrofit.create(ApiInterface::class.java)
        }
    }
}