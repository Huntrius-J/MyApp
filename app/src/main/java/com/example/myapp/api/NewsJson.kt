package com.example.myapp.api

data class NewsJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)