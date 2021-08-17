package com.mimarketplace.domain.category.usecases

import com.mimarketplace.domain.category.out.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {

    fun invoke() = categoryRepository.getCategories()

}