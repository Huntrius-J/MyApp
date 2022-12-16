package com.example.myapp

import com.example.myapp.api.Article
import com.example.myapp.api.NewsJson
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {
    @GET("everything?q=cats&from=2022-11-15&sortBy=publishedAt&apiKey=3bdb8b1910c84ccb9c1de6c7e4793a86")
    fun getNews(): Call<NewsJson>
}