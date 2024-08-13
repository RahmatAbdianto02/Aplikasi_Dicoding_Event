package com.dicoding.aplikasidicodingevent.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasidicodingevent.RecycleView.EventResponse
import com.dicoding.aplikasidicodingevent.RecycleView.ListEventsItem
import com.dicoding.aplikasidicodingevent.databinding.FragmentDashboardBinding
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
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
        val root: View = binding.root


//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.recycleApiFinish.layoutManager = layoutManager


        fetchEventDataFinish()
    }

    private fun fetchEventDataFinish(){
        val apiService = ApiConfig.getApiService()
        apiService.getFinishedEvents().enqueue(object :Callback<EventResponse>{
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        setupRecyclerView(responseBody.listEvents)
                    }
                } else {
                    Log.e("FinishFragment","onFailure: ${response.message()}")
                }
            }
            private fun setupRecyclerView(events: List<ListEventsItem>) {
                val adapter = EventAdapter(requireContext(), events)
                binding.recycleApiFinish.adapter = adapter
            }


            private fun showLoading(isLoading: Boolean) {
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }


            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showLoading(false)
                Log.e("FinishFragment", "onFailure: ${t.message}")
            }

        })
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}