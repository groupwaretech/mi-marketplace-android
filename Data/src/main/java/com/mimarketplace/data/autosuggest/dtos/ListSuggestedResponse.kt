package com.mimarketplace.data.autosuggest.dtos

import com.mimarketplace.domain.autosuggest.models.Suggest

data class ListSuggestedResponse(
    val q: String,
    val suggested_queries: List<Suggest>,
)
