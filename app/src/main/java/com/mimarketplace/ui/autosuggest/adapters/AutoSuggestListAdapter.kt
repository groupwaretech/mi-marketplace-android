package com.mimarketplace.ui.autosuggest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimarketplace.databinding.AdapterAutoSuggestListBinding
import com.mimarketplace.domain.autosuggest.models.Suggest

class AutoSuggestListAdapter (private val onItemClicked: (Suggest) -> Unit) : ListAdapter<Suggest, RecyclerView.ViewHolder>(
    SuggestDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SuggestViewHolder(
            AdapterAutoSuggestListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context,
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as SuggestViewHolder).bind(item)
    }

    class SuggestViewHolder(
        private val binding: AdapterAutoSuggestListBinding,
        private val context: Context,
        onItemClicked: (Suggest) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.suggest?.let { suggest ->
                    onItemClicked(suggest)
                }
            }
        }

        fun bind(item: Suggest) {

            binding.apply {
                suggest = item
                executePendingBindings()
            }
        }
    }

}

private class SuggestDiffCallback : DiffUtil.ItemCallback<Suggest>() {

    override fun areItemsTheSame(oldItem: Suggest, newItem: Suggest): Boolean {
        return oldItem.q == newItem.q
    }

    override fun areContentsTheSame(oldItem: Suggest, newItem: Suggest): Boolean {
        return oldItem == newItem
    }
}