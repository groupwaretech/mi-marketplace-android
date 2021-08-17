package com.mimarketplace.domain.item.usecases

import com.mimarketplace.domain.item.out.ItemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetItemsByTextUseCase @Inject constructor(private val itemRepository: ItemRepository){

    fun invoke(q: String, offset: Int) = itemRepository.getItemsByText(q, offset)
}