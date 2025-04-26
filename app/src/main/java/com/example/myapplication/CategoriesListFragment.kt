package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.myapplication.RecipesListFragment.Companion.ARG_CATEGORY_ID
import com.example.myapplication.RecipesListFragment.Companion.ARG_CATEGORY_IMAGE_URL
import com.example.myapplication.RecipesListFragment.Companion.ARG_CATEGORY_NAME
import com.example.myapplication.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding: FragmentListCategoriesBinding
        get() = _binding ?: throw IllegalStateException("Binding is null. View might be destroyed.")

    private lateinit var categoriesListAdapter: CategoriesListAdapter

    fun onItemClick(categoryId: Int) {
        openRecipesByCategoryId(categoryId)
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category = STUB.getCategories().firstOrNull { it.id == categoryId }
            ?: STUB.getCategories().first()

        val bundle = bundleOf(
            ARG_CATEGORY_ID to category.id,
            ARG_CATEGORY_NAME to category.title,
            ARG_CATEGORY_IMAGE_URL to category.imageUrl
        )

        val recipesListFragment = RecipesListFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, recipesListFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter(STUB.getCategories())
        binding.rvCategories.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }
}