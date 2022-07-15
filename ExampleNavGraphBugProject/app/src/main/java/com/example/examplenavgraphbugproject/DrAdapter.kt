package com.example.examplenavgraphbugproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examplenavgraphbugproject.databinding.DrItemBinding


class DrAdapter(val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var doctors: ArrayList<Doctor?> = ArrayList()
    private var locationName: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DrViewHolder(
            DrItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DrViewHolder -> {
                doctors[position]?.let { holder.bind(locationName, it) }
            }
        }
    }

    interface Listener {

        fun onDrSelected(doctor: Doctor)

        fun onNoDrSelected()

    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun setLocationDrs(locationDrs: List<Doctor>) {
        this.doctors.clear()
        this.doctors.addAll(locationDrs)
        this.doctors.add(null)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}
