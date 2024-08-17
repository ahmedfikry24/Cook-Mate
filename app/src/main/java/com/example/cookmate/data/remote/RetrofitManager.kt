package com.example.cookmate.data.remote

import com.example.cookmate.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    private val gson = GsonBuilder().create()
    private val gsonConverter = GsonConverterFactory.create(gson)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BaseUrl)
        .addConverterFactory(gsonConverter)
        .build()


    val service: RetrofitServices = retrofit.create(RetrofitServices::class.java)
}