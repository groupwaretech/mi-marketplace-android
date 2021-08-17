package com.mimarketplace.data.category.remote

import com.mimarketplace.data.utils.api.*
import com.mimarketplace.domain.category.models.Category


class CategoryApi(basePath: kotlin.String) : ApiClient(basePath) {

    /**
     * Busca todas las sedes activas
     * Busca todas las sedes activas
     * @param account Nombre del esquema que se asocia a la cuenta en la que se desea realizar el proceso
     * @param listCityId Lista id de ciudades separadas por coma (optional)
     * @param onlyInZone Buscar solo las sedes con zonas (optional)
     * @return ApiResponseList
     */
    @Suppress("UNCHECKED_CAST")
    fun getAllCategories(siteId: String = "MCO"): List<Category> {

        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/sites/{SITE_ID}/categories".replace("{SITE_ID}", "$siteId")
        )
        val response = request<List<Category>>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as List<Category>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: Constants.HTTP_CLIENT_ERROR_MSG)
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: Constants.HTTP_SERVER_ERROR_MSG)
        }
    }

}