package com.mimarketplace.ui.autosuggest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mimarketplace.databinding.FragmentAutoSuggestListBinding
import com.mimarketplace.domain.autosuggest.models.Suggest
import com.mimarketplace.domain.autosuggest.usecases.GetSuggestUseCase
import com.mimarketplace.ui.autosuggest.adapters.AutoSuggestListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [AutoSuggestListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AutoSuggestListFragment : Fragment() {

    @Inject
    lateinit var getSuggestUseCase: GetSuggestUseCase

    lateinit var onItemClick: (Suggest) -> Unit

    private val adapter = AutoSuggestListAdapter{
        onItemClick(it)
    }

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
        val binding = FragmentAutoSuggestListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.autoSuggestList.adapter = adapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AutoSuggestListFragment.
         */
        @JvmStatic
        fun newInstance(onItemClick: ((Suggest) -> Unit)): AutoSuggestListFragment {
            val autoSuggestListFragment = AutoSuggestListFragment()
            autoSuggestListFragment.onItemClick = onItemClick
            return autoSuggestListFragment
        }

    }

    var onSearch: (String) -> Unit = {
        GlobalScope.launch(Dispatchers.IO) { // launch a new coroutine in background and continue
            try {
                if (it.isEmpty()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        val suggest = listOf<Suggest>()
                        adapter.submitList(suggest)
                    }
                    return@launch
                }

                val suggest = getSuggestUseCase.invoke(it)
                GlobalScope.launch(Dispatchers.Main) {
                    adapter.submitList(suggest)
                }
            } catch (e : Error) {
                println("--Error: " + e.message)
            }
        }
    }
}