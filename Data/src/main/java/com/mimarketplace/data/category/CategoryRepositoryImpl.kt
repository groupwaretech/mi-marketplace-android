package com.mimarketplace.data.category

import com.mimarketplace.data.category.remote.CategoryApi
import com.mimarketplace.domain.category.out.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val categoryApi: CategoryApi) : CategoryRepository  {

    override fun getCategories() = categoryApi.getAllCategories()
}