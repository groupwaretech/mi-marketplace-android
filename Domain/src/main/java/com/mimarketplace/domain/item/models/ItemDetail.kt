package com.mimarketplace.domain.item.models

data class ItemDetail(
    val id: String,
    val title: String,
    val thumbnail_id: String?,
    val price: Double,
    val shipping: Shipping,
    val pictures: List<Picture>,
)
