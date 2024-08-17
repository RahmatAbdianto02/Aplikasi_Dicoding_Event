package com.dicoding.aplikasidicodingevent.Detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Mendapatkan eventId dari intent
        val eventId = intent.getStringExtra("EVENT_ID") ?: return
        fetchEventDetail(eventId)
    }

    // Metode untuk mem-fetch detail event berdasarkan id
    private fun fetchEventDetail(id: String) {
        val apiService = ApiConfig.getApiService()
        apiService.getDetailEvent(id).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (response.isSuccessful) {
                    val event = response.body()?.event
                    if (event != null) {
                        displayEventDetail(event)
                    }
                } else {
                    Log.e("DetailActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e("DetailActivity", "onFailure: ${t.message}")
            }
        })
    }

    // Metode untuk menampilkan detail event
    private fun displayEventDetail(event: Event) {
        findViewById<ImageView>(R.id.detailImage).let {
            Glide.with(this).load(event.mediaCover).into(it)
        }
        findViewById<TextView>(R.id.detailTitle).text = event.name
        findViewById<TextView>(R.id.detailDescription).text = HtmlCompat.fromHtml(event.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)

        findViewById<Button>(R.id.buttonDetail).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(event.link)
            }
            startActivity(intent)  // Menggunakan `intent` di sini, bukan `it`
        }
    }
}
