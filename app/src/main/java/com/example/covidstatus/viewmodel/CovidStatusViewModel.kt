package com.example.covidstatus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidstatus.models.status
import com.example.covidstatus.repository.CovidStatusRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CovidStatusViewModel:ViewModel() {
    val repository = CovidStatusRepository()
    val data:MutableLiveData<Response<status>> = MutableLiveData()
    fun getData()
    {
        viewModelScope.launch {
            data.value=repository.getCovidStatus()
        }
    }
}