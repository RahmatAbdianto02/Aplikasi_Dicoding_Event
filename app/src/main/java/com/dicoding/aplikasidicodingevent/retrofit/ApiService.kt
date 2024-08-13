package com.dicoding.aplikasidicodingevent.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse

interface ApiService {
    @GET("events")
    fun getActiveEvents(
        @Query("active") active: Boolean = true
    ): Call<EventResponse>
}
