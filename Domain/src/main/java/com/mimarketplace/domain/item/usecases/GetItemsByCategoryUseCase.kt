package com.mimarketplace.domain.item.usecases

import com.mimarketplace.domain.item.out.ItemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetItemsByCategoryUseCase @Inject constructor(private val itemRepository: ItemRepository){

    fun invoke(categoryId: String, offset: Int) = itemRepository.getItemsByCategory(categoryId, offset)
}