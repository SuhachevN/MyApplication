package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MethodAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStep: TextView = itemView.findViewById(R.id.tvStep)
        fun bind(step: String, position: Int) {
            tvStep.text = "${position + 1}. $step"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_method, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MethodAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }

    override fun getItemCount(): Int = dataSet.size
}