package com.example.covidstatus.api

import com.example.covidstatus.models.status
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("covid19-in/stats/latest")
    suspend fun getStatus(): Response<status>
}