package com.mimarketplace.data.di

import com.mimarketplace.data.BuildConfig
import com.mimarketplace.data.autosuggest.remote.AutosuggestApi
import com.mimarketplace.data.category.remote.CategoryApi
import com.mimarketplace.data.item.remote.ItemApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideCategoryApi(): CategoryApi = CategoryApi(BuildConfig.BASE_URL)

    @Provides
    fun provideAutosuggestApi(): AutosuggestApi = AutosuggestApi(BuildConfig.BASE_URL)

    @Provides
    fun provideItemApi(): ItemApi = ItemApi(BuildConfig.BASE_URL)
}