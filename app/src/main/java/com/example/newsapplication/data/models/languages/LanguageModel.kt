package com.example.newsapplication.data.models.languages

data class LanguageModel(
    val languageName: String,
    val languageCode: String,
    var isSelected: Boolean = false

)
