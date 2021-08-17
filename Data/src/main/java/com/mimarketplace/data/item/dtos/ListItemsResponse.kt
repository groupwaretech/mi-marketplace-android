package com.mimarketplace.data.item.dtos

import com.mimarketplace.domain.item.models.Item

data class ListItemsResponse(
    //val query: String,
    val paging: Paging,
    val results: List<Item>,
)
