package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IngredientsAdapter(
    private val dataSet: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvIngredient: TextView = itemView.findViewById(R.id.tvIngredient)
        fun bind(ingredient: Ingredient) {
            tvIngredient.text = "${ingredient.quantity} ${ingredient.unitOfMeasure} ${ingredient.description}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}