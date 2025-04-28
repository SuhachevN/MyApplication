package com.example.myapplication

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRecipeBinding
import java.io.IOException

class RecipesListAdapter(
    private val dataSet: List<Recipe>
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = object : OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                listener(recipeId)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) = with(binding) {
            tvRecipeTitle.text = recipe.title
            ivRecipeImage.loadImageFromAssets(recipe.imageUrl)
            ivRecipeImage.contentDescription = itemView.context.getString(
                R.string.recipe_image_description,
                recipe.title
            )

            itemView.setOnClickListener {
                itemClickListener?.onItemClick(recipe.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    private fun ImageView.loadImageFromAssets(assetPath: String) {
        try {
            context.assets.open(assetPath).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                if (this is androidx.appcompat.widget.AppCompatImageView) {
                    setImageDrawable(drawable)
                }
            }
        } catch (e: IOException) {
            Log.e("RecipesListAdapter", "Ошибка при загрузке изображения из assets: $assetPath", e)
        }
    }
}
