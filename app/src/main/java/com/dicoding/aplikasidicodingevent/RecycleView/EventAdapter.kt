package com.dicoding.aplikasidicodingevent.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.RecycleView.ListEventsItem

class EventAdapter(
    private val context: Context,
    private val events: List<ListEventsItem>,
    private val onItemClick: (String) -> Unit // <-- Added parameter
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_row_image, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.itemText.text = event.name

        Glide.with(context)
            .load(event.mediaCover)
            .placeholder(R.drawable.baseline_image_24)
            .into(holder.itemImage)

        // Set click listener
        holder.itemView.setOnClickListener {
            onItemClick(event.id.toString()) // Adjust based on your Event class
        }
    }

    override fun getItemCount(): Int = events.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_Image)
        val itemText: TextView = itemView.findViewById(R.id.item_Text)
    }
}
