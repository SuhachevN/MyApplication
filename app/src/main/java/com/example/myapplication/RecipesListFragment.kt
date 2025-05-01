package com.example.myapplication

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentListRecipesBinding
import java.io.IOException
import java.io.InputStream

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {

    private var _binding: FragmentListRecipesBinding? = null
    private val binding: FragmentListRecipesBinding
        get() = _binding ?: throw IllegalStateException("Binding is null. View might be destroyed.")

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    companion object {
        const val ARG_CATEGORY_ID = "categoryId"
        const val ARG_CATEGORY_NAME = "categoryName"
        const val ARG_CATEGORY_IMAGE_URL = "categoryImageUrl"
        const val ARG_RECIPE = "recipe"
    }

    private fun initUi() {
        categoryId?.let { id ->
            val recipes = STUB.getRecipesByCategoryId(id)
            val recipesAdapter = RecipesListAdapter(recipes)
            recipesAdapter.setOnItemClickListener { recipeId ->
                openRecipeByRecipeId(recipeId)
            }
            binding.rvRecipes.adapter = recipesAdapter
        }

        categoryName?.let { name ->
            binding.tvRecipesHeaderTitle.text = name
        }

        categoryImageUrl?.let { imageUrl ->
            try {
                val inputStream: InputStream = requireContext().assets.open(imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivRecipesHeader.setImageDrawable(drawable)
            } catch (e: IOException) {
                Log.e("RecipesListFragment", "Ошибка при загрузке изображения", e)
            }
        }
    }

    private fun openRecipeByRecipeId(recipeId: Int) {

        val recipe = STUB.getRecipeById(recipeId)
        recipe?.let {
            val bundle = Bundle().apply {
                putParcelable(ARG_RECIPE, it)
            }

            val recipeFragment = RecipeFragment().apply {
                arguments = bundle
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, recipeFragment)
                .addToBackStack(null)
                .commit()
        } ?: run {
            Log.e("RecipesListFragment", "Рецепт с ID $recipeId не найден.")
        }
    }


    private fun initBundleData() {
        arguments?.let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
            categoryName = it.getString(ARG_CATEGORY_NAME)
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBundleData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}