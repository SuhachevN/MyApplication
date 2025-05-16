package com.example.myapplication

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import java.io.IOException

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private val binding: FragmentRecipeBinding
        get() = _binding ?: throw IllegalStateException("Binding is null. View might be destroyed.")

    private var recipe: Recipe? = null

    companion object {
        private const val ARG_RECIPE = "arg_recipe"

        fun newInstance(recipe: Recipe): RecipeFragment {
            return RecipeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_RECIPE, recipe)
                }
            }
        }
    }

    private fun initUi() {
        binding.tvRecipeTitle.text = recipe?.title
        recipe?.let { binding.ivRecipeCard.loadImageFromAssets(it.imageUrl) }
    }

    fun ImageView.loadImageFromAssets(assetPath: String) {
        try {
            context.assets.open(assetPath).use { inputStream ->
                val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
                this.setImageDrawable(drawable)
            }
        } catch (e: IOException) {
            Log.e("RecipeFragment", "Error loading image from assets: $assetPath", e)
        }
    }

    private fun initRecycler() {
        binding.rvIngredients.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipe?.let { IngredientsAdapter(it.ingredients) }
            addItemDecoration(
                MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
        }

        binding.rvMethod.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipe?.let { MethodAdapter(it.method) }
            addItemDecoration(
                MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_RECIPE, Recipe::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_RECIPE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}