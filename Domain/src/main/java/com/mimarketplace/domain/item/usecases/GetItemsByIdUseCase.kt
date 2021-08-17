package com.mimarketplace.domain.item.usecases

import com.mimarketplace.domain.item.out.ItemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetItemsByIdUseCase @Inject constructor(private val itemRepository: ItemRepository){

    fun invoke(itemId: String) = itemRepository.getItemById(itemId)
}