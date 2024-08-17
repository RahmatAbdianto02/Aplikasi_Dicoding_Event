package com.dicoding.aplikasidicodingevent.retrofit

import com.dicoding.aplikasidicodingevent.Detail.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse
import retrofit2.http.Path

interface ApiService {
    @GET("events")
    fun getActiveEvents(
        @Query("active") active: Int = 40
    ): Call<EventResponse>

    @GET("events")
    fun getFinishEvents(
        @Query("active") active: Int = 0
    ): Call<EventResponse>
    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailResponse>
}
