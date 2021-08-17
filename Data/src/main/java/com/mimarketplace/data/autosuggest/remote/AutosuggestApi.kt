package com.mimarketplace.data.autosuggest.remote

import com.mimarketplace.data.autosuggest.dtos.ListSuggestedResponse
import com.mimarketplace.data.utils.api.*

class AutosuggestApi(basePath: kotlin.String) : ApiClient(basePath) {

    fun search(q: String, limit: Int, showFilters: Boolean, siteId: String = "MCO"): ListSuggestedResponse {
        val localVariableQuery: Map<String, List<String>> = mapOf("q" to listOf("$q"), "limit" to listOf("$limit"), "showFilters" to listOf("$showFilters"))
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/sites/{SITE_ID}/autosuggest".replace("{SITE_ID}", "$siteId"),
            query = localVariableQuery
        )
        val response = request<ListSuggestedResponse>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as ListSuggestedResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: Constants.HTTP_CLIENT_ERROR_MSG)
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: Constants.HTTP_SERVER_ERROR_MSG)
        }
    }
}