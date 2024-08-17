package com.dicoding.aplikasidicodingevent.ui.dashboard

import EventAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasidicodingevent.Detail.DetailActivity
import com.dicoding.aplikasidicodingevent.databinding.FragmentDashboardBinding
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse
import com.dicoding.aplikasidicodingevent.RecycleView.ListEventsItem

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menetapkan LayoutManager sebelum Adapter
        val layoutManager = LinearLayoutManager(context)
        binding.recycleApi.layoutManager = layoutManager // Menetapkan LayoutManager

        // Memulai fetch data dari API
        fetchEventData()
    }

    private fun fetchEventData() {
        showLoading(true)
        val apiService = ApiConfig.getApiService()

        apiService.getFinishEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Memastikan list events tidak null dan tidak mengandung nilai null
                        val nonNullEvents: List<ListEventsItem> = responseBody.listEvents.filterNotNull()
                        setupRecyclerView(nonNullEvents)
                    } else {
                        Log.e("DashboardFragment", "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showLoading(false)
                Log.e("DashboardFragment", "onFailure: ${t.message}")
            }
        })
    }

//    private fun setupRecyclerView(events: List<ListEventsItem>) {
//         Menyesuaikan adapter dengan list non-nullable events
//        val adapter = EventAdapter(requireContext(), events)
//        binding.recycleApi.adapter = adapter // Menetapkan adapter ke RecyclerView
//    }
private fun setupRecyclerView(events: List<ListEventsItem>) {
    val adapter = EventAdapter(requireContext(), events) { eventId ->
        // Handle item click
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("EVENT_ID", eventId)
        }
        startActivity(intent)
    }
    binding.recycleApi.adapter = adapter
}

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
