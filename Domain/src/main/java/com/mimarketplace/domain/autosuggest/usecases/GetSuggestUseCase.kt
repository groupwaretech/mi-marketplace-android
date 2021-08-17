package com.mimarketplace.domain.autosuggest.usecases

import com.mimarketplace.domain.autosuggest.out.AutosuggestRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSuggestUseCase @Inject constructor(private val autosuggestRepository: AutosuggestRepository) {

    fun invoke(q: String, limit: Int = 6, showFilters: Boolean = false) = autosuggestRepository.getSuggest(q, limit, showFilters)
}