package com.mimarketplace.data.item.remote

import com.mimarketplace.data.autosuggest.dtos.ListSuggestedResponse
import com.mimarketplace.data.item.dtos.ListItemsResponse
import com.mimarketplace.data.utils.api.*
import com.mimarketplace.domain.item.models.ItemDetail

class ItemApi (basePath: kotlin.String) : ApiClient(basePath) {

    fun search(q: String, offset: Int, siteId: String = "MCO"): ListItemsResponse {
        val limit = 20
        val localVariableQuery: Map<String, List<String>> = mapOf("q" to listOf("$q"), "offset" to listOf("$offset"), "limit" to listOf("$limit"))
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/sites/{SITE_ID}/search".replace("{SITE_ID}", "$siteId"),
            query = localVariableQuery
        )
        val response = request<ListItemsResponse>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as ListItemsResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: Constants.HTTP_CLIENT_ERROR_MSG)
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: Constants.HTTP_SERVER_ERROR_MSG)
        }
    }

    fun searchByCategory(category: String, offset: Int, siteId: String = "MCO"): ListItemsResponse {
        val limit = 20
        val localVariableQuery: Map<String, List<String>> = mapOf("category" to listOf("$category"), "offset" to listOf("$offset"), "limit" to listOf("$limit"))
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/sites/{SITE_ID}/search".replace("{SITE_ID}", "$siteId"),
            query = localVariableQuery
        )
        val response = request<ListItemsResponse>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as ListItemsResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: Constants.HTTP_CLIENT_ERROR_MSG)
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: Constants.HTTP_SERVER_ERROR_MSG)
        }
    }

    fun searchById(itemId: String): ItemDetail {
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/items/{itemId}".replace("{itemId}", "$itemId"),
        )
        val response = request<ItemDetail>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as ItemDetail
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: Constants.HTTP_CLIENT_ERROR_MSG)
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: Constants.HTTP_SERVER_ERROR_MSG)
        }
    }

}