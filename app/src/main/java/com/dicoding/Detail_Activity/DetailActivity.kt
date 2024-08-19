package com.dicoding.Detail_Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val eventId = intent.getStringExtra("EVENT_ID") ?: return
        fetchEventDetail(eventId)
    }

    private fun fetchEventDetail(id: String) {
        val apiService = ApiConfig.getApiService()
        apiService.getDetailEvent(id).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (response.isSuccessful) {
                    val event = response.body()?.event
                    if (event != null) {
                        displayEventDetails(event)
                    }
                } else {
                    Log.e("DetailActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Failed to load event details", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayEventDetails(event: Event) {
        findViewById<ImageView>(R.id.detailImage).let {
            Glide.with(this).load(event.mediaCover).into(it)
        }
        findViewById<TextView>(R.id.detailTitle).text = event.name
        findViewById<TextView>(R.id.detailDescription).text = HtmlCompat.fromHtml(event.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
        findViewById<Button>(R.id.buttonDetail).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(event.link)
            }
            startActivity(intent)
        }
    }
}
