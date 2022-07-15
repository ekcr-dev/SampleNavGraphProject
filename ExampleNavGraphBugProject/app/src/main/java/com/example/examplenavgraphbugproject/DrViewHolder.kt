package com.example.examplenavgraphbugproject

import androidx.recyclerview.widget.RecyclerView
import com.example.examplenavgraphbugproject.databinding.DrItemBinding

class DrViewHolder(
    private val binding: DrItemBinding,
    val listener: DrAdapter.Listener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(locationName: String, doctor: Doctor) {
        itemView.setOnClickListener { listener.onDrSelected(doctor) }
        binding.titleTextView.text = doctor.name
        binding.locationNameTextView.text = locationName
    }
}