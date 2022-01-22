package com.example.covidstatus.models

data class Data(
    val regional: List<Regional>,
    val summary: Summary,
    val unofficialsummary: List<UnofficialSummary>
)