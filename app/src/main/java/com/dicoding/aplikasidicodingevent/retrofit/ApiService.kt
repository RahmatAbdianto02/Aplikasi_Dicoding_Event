package com.dicoding.aplikasidicodingevent.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse

interface ApiService {
    @GET("events")
    fun getUpcomingEvents(
        @Query("active") active: Int = 1
    ): Call<EventResponse>

    @GET("events")
    fun getFinishedEvents(
        @Query("active") active: Int = 0
    ):Call<EventResponse>


}
