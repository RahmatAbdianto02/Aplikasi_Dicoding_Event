package com.dicoding.aplikasidicodingevent.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.Detail_Activity.DetailActivity
import com.dicoding.aplikasidicodingevent.databinding.FragmentDashboardBinding
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse
import com.dicoding.aplikasidicodingevent.RecycleView.ListEventsItem
import com.dicoding.aplikasidicodingevent.ui.home.EventAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.recycleApiFinish.layoutManager = layoutManager

        fetchEventDataFinish()
    }

    private fun fetchEventDataFinish() {
        val apiService = ApiConfig.getApiService()
        apiService.getFinishedEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val eventList = response.body()?.listEvents
                    if (eventList != null) {
                        setupRecyclerView(eventList)
                    }
                } else {
                    Log.e("DashboardFragment", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("DashboardFragment", "Failure: ${t.message}")
            }
        })
    }

    private fun setupRecyclerView(events: List<ListEventsItem>) {
        val adapter = EventAdapter(requireContext(), events) { eventId ->
            // Handle item click
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                // Convert eventId to String if it's an Int
                putExtra("EVENT_ID", eventId.toString())
            }
            startActivity(intent)
        }
        binding.recycleApiFinish.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
