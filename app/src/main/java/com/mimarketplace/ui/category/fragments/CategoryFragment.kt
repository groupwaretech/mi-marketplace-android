package com.mimarketplace.ui.category.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mimarketplace.databinding.FragmentCategoryBinding
import com.mimarketplace.domain.category.models.MainCategory
import com.mimarketplace.ui.category.adapters.MainCategoryAdapter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ON_ITEM_CLICK_FUN = "onItemClick"
/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {

    private lateinit var onItemClick: (MainCategory) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentCategoryBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = MainCategoryAdapter {
            onItemClick(it)
        }

        binding.categoryList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CategoryFragment.
         */
        @JvmStatic
        fun newInstance(onItemClick: ((MainCategory) -> Unit)): CategoryFragment {
            val categoryFragment = CategoryFragment()
            categoryFragment.onItemClick = onItemClick

            return categoryFragment
        }

    }

    private fun subscribeUi(adapter: MainCategoryAdapter) {
        val categories = listOf(MainCategory("MCO1051", "Celulares y Teléfonos", "ic_baseline_phone_iphone_24")
            , MainCategory("MCO1648", "Computación", "ic_baseline_desktop_windows_24")
            , MainCategory("MCO1000", "Electrónica, Audio y Video", "ic_outline_headset_mic_24")
            , MainCategory("MCO1276", "Deportes y Fitness", "ic_outline_sports_soccer_24")
            , MainCategory("MCO1743", "Carros motos y otros", "ic_outline_directions_car_24"))
        adapter.submitList(categories)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}