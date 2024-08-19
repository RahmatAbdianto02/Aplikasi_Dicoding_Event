package com.dicoding.aplikasidicodingevent.retrofit

import com.dicoding.Detail_Activity.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse
import retrofit2.http.Path

interface ApiService {
    @GET("events")
    fun getUpcomingEvents(
        @Query("active") active: Int = 1
    ): Call<EventResponse>

    @GET("events")
    fun getFinishedEvents(
        @Query("active") active: Int = 0
    ): Call<EventResponse>

    // Tambahkan di sini
    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailResponse>
}
