package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private val binding: FragmentRecipeBinding
        get() = _binding ?: throw IllegalStateException("Binding is null. View might be destroyed.")

    private var recipeId: Int? = null

    companion object {
        private const val ARG_RECIPE_ID = "recipeId"

        fun newInstance(recipeId: Int): RecipeFragment {
            return RecipeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_RECIPE_ID, recipeId)
                }
            }
        }
    }

    private fun initUi() {
        recipeId?.let {
            binding.tvRecipeId.text = "Recipe ID: $it"
        } ?: run {
            binding.tvRecipeId.text = "Recipe ID is not available"
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getInt(ARG_RECIPE_ID)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}