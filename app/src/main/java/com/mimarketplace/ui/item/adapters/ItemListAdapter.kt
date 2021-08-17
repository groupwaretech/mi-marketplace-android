package com.mimarketplace.ui.item.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimarketplace.data.BuildConfig
import com.mimarketplace.databinding.AdapterItemListBinding
import com.mimarketplace.domain.item.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class ItemListAdapter(private val onItemClicked: (Item) -> Unit) : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            AdapterItemListBinding.inflate(
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
        (holder as ItemViewHolder).bind(item)
    }


    class ItemViewHolder(
        private val binding: AdapterItemListBinding,
        private val context: Context,
        onItemClicked: (Item) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.item?.let { item ->
                    onItemClicked(item)
                }
            }
        }

        fun bind(item_: Item) {

            GlobalScope.launch(Dispatchers.IO) {
                try {

                    val url = URL(BuildConfig.BASE_IMAGE_URL + "D_NQ_NP_${item_.thumbnail_id}-V.webp")
                    val bmp: Bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    GlobalScope.launch(Dispatchers.Main) {
                        binding.itemImageView.setImageBitmap(bmp)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.apply {
                item = item_
                executePendingBindings()
            }
        }
    }
}

private class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}