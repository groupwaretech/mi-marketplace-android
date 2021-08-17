package com.mimarketplace.data.di

import com.mimarketplace.data.autosuggest.AutosuggestRepositoryImpl
import com.mimarketplace.data.category.CategoryRepositoryImpl
import com.mimarketplace.data.item.ItemRepositoryImpl
import com.mimarketplace.domain.autosuggest.out.AutosuggestRepository
import com.mimarketplace.domain.category.out.CategoryRepository
import com.mimarketplace.domain.item.out.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindAutosuggestRepository(autosuggestRepositoryImpl: AutosuggestRepositoryImpl): AutosuggestRepository

    @Binds
    @Singleton
    abstract fun bindItemRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository

}