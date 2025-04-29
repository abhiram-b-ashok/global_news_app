package com.example.newsapplication.data.models.countries

data class CountryModel(
    val countryName: String,
    val countryCode: String,
    var isSelected: Boolean = false
)
