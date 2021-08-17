package com.mimarketplace.data.item

import com.mimarketplace.data.item.remote.ItemApi
import com.mimarketplace.domain.item.models.Item
import com.mimarketplace.domain.item.models.ItemDetail
import com.mimarketplace.domain.item.out.ItemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(private val itemApi: ItemApi) : ItemRepository{


    override fun getItemsByText(q: String, offset: Int): List<Item> {
        var itemsQuery = itemApi.search(q, offset)
        return itemsQuery.results
    }

    override fun getItemsByCategory(categoryId: String, offset: Int): List<Item> {
        var itemsQuery = itemApi.searchByCategory(categoryId, offset)
        return itemsQuery.results
    }

    override fun getItemById(id: String): ItemDetail {
        return itemApi.searchById(id)
    }
}