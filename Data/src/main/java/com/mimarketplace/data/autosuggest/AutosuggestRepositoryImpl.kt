package com.mimarketplace.data.autosuggest

import com.mimarketplace.data.autosuggest.remote.AutosuggestApi
import com.mimarketplace.domain.autosuggest.models.Suggest
import com.mimarketplace.domain.autosuggest.out.AutosuggestRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AutosuggestRepositoryImpl @Inject constructor(private val autosuggestApi: AutosuggestApi) : AutosuggestRepository {

    override fun getSuggest(q: String, limit: Int, showFilters: Boolean): List<Suggest> {
        var suggestedQuery = autosuggestApi.search(q, limit, showFilters)
        return suggestedQuery.suggested_queries
    }

}