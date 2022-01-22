package com.example.covidstatus.models

data class status(
    val data: Data,
    val lastOriginUpdate: String,
    val lastRefreshed: String,
    val success: Boolean
)