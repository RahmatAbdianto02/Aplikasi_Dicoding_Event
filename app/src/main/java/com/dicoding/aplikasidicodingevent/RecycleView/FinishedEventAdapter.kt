package com.dicoding.aplikasidicodingevent.RecycleView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.databinding.ItemImageFinishBinding

class FinishedEventAdapter(
    private val context: Context,
    private val events: List<ListEventsItem>
) : RecyclerView.Adapter<FinishedEventAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: ItemImageFinishBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.apply {
                // Set event title or fallback to "No Title"
                textDashboard.text = event.name ?: "No Title"

                // Set event description or fallback to "No Description"
                // Assuming `eventSubtitle` refers to a different TextView in the actual layout
                // In this case, I'm using textDashboard as a placeholder
                textDashboard.text = event.summary ?: event.description ?: "No Description"

                // Load event image using Glide, fallback to a default drawable if image URL is null
                Glide.with(context)
                    .load(event.mediaCover ?: event.imageLogo ?: R.drawable.baseline_image_24)
                    .centerCrop()
                    .into(eventImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemImageFinishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size
}
