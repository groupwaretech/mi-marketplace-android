package com.mimarketplace.ui.category.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimarketplace.databinding.AdapterMainCategoryListBinding
import com.mimarketplace.domain.category.models.MainCategory

class MainCategoryAdapter(private val onItemClicked: (MainCategory) -> Unit) : ListAdapter<MainCategory, RecyclerView.ViewHolder>(CategoryDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            AdapterMainCategoryListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context,
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = getItem(position)
        (holder as CategoryViewHolder).bind(category)
    }


    class CategoryViewHolder(
        private val binding: AdapterMainCategoryListBinding,
        private val context: Context,
        onItemClicked: (MainCategory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.category?.let { category ->
                    onItemClicked(category)
                }
            }
        }

        fun bind(item: MainCategory) {

            binding.mainCategoryImage.setImageResource(context.resources.getIdentifier("drawable/${item.img}", null, context.packageName))

            binding.apply {
                category = item
                executePendingBindings()
            }
        }
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<MainCategory>() {

    override fun areItemsTheSame(oldItem: MainCategory, newItem: MainCategory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MainCategory, newItem: MainCategory): Boolean {
        return oldItem == newItem
    }
}