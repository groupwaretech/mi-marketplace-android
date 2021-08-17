package com.mimarketplace.ui.item.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mimarketplace.databinding.FragmentItemListBinding
import com.mimarketplace.domain.category.models.MainCategory
import com.mimarketplace.domain.item.models.Item
import com.mimarketplace.domain.item.usecases.GetItemsByCategoryUseCase
import com.mimarketplace.domain.item.usecases.GetItemsByTextUseCase
import com.mimarketplace.ui.item.adapters.ItemListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_FILTER_BY = "filterBy"
private const val ARG_FILTER_VALUE = "filterValue"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ItemListFragment : Fragment() {

    @Inject
    lateinit var getItemsByTextUseCase: GetItemsByTextUseCase

    @Inject
    lateinit var getItemsByCategoryUseCase: GetItemsByCategoryUseCase

    private var filterBy: String? = null
    private var filterValue: String? = null

    private var offset = 0
    private var page = 1
    private var items = listOf<Item>()

    private lateinit var onItemClick: (Item) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filterBy = it.getString(ARG_FILTER_BY)
            filterValue = it.getString(ARG_FILTER_VALUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentItemListBinding.inflate(inflater, container, false)
        context ?: return binding.root


        val adapter = ItemListAdapter{
            onItemClick(it)
        }
        binding.itemList.adapter = adapter
        subscribeUi(adapter)

        // initialize an instance of linear layout manager
        val layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        ).apply {
            // specify the layout manager for recycler view
            binding.itemList.layoutManager = this
        }

        // initialize an instance of divider item decoration
        DividerItemDecoration(
            context, // context
            layoutManager.orientation
        ).apply {
            // add divider item decoration to recycler view
            // this will show divider line between items
            binding.itemList.addItemDecoration(this)
        }

        binding.itemList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    print("--END")
                    page++
                    offset = page*20 + 1
                    subscribeUi(adapter)
                }
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param filterBy Parameter 1.
         * @return A new instance of fragment ItemListFragment.
         */
        @JvmStatic
        fun newInstance(filterBy: String, filterValue: String, onItemClick: ((Item) -> Unit)): ItemListFragment {
            val itemListFragment = ItemListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FILTER_BY, filterBy)
                    putString(ARG_FILTER_VALUE, filterValue)
                }
            }

            itemListFragment.onItemClick = onItemClick

            return itemListFragment
        }

    }

    private fun subscribeUi(adapter: ItemListAdapter) {
        GlobalScope.launch(Dispatchers.IO) { // launch a new coroutine in background and continue
            try {
                var listitems = listOf<Item>()
                when (filterBy) {
                    "CATEGORY" -> listitems = getItemsByCategoryUseCase.invoke(categoryId = filterValue!!, offset)
                    "TEXT" -> listitems = getItemsByTextUseCase.invoke(q = filterValue!!, offset)
                }

                println("--items: $listitems")
                items += listitems

                GlobalScope.launch(Dispatchers.Main) {
                    adapter.submitList(items)
                }
            } catch (e : Error) {
                println("--Error: " + e.message)
            }

        }
    }
}