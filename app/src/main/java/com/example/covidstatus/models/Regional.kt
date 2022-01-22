package com.example.covidstatus.models

data class Regional(
    val confirmedCasesForeign: Int,
    val confirmedCasesIndian: Int,
    val deaths: Int,
    val discharged: Int,
    val loc: String,
    val totalConfirmed: Int
)