package com.example.covidstatus.repository

import com.example.covidstatus.api.RetrofitInstance

class CovidStatusRepository {
    suspend fun getCovidStatus()=RetrofitInstance.api.getStatus()
}