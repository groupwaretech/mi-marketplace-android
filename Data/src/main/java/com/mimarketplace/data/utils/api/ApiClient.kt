package com.mimarketplace.data.utils.api

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody


open class ApiClient(val baseUrl: String) {

    companion object {
        protected const val Accept = "Accept"
        protected const val JsonMediaType = "application/json"

        @JvmStatic
        var defaultHeaders: Map<String, String> by ApplicationDelegates.setOnce(mapOf(Accept to JsonMediaType))

    }

    var client: OkHttpClient = OkHttpClient()

    protected inline fun <reified T : Any?> responseBody(body: ResponseBody?, mediaType: String = JsonMediaType): T? {
        if (body == null) return null
        return when (mediaType) {
            JsonMediaType -> Serializer.moshi.adapter(T::class.java).fromJson(body.source())
            else -> TODO()
        }
    }

    protected inline fun <reified T : Any?> request(requestConfig: RequestConfig): ApiInfrastructureResponse<T?> {
        val httpUrl = baseUrl.toHttpUrlOrNull() ?: throw IllegalStateException("baseUrl is invalid.")

        var urlBuilder = httpUrl.newBuilder()
            .addPathSegments(requestConfig.path.trimStart('/'))

        for (query in requestConfig.query) {
            for (queryValue in query.value) {
                if (queryValue != "null") {
                    urlBuilder = urlBuilder.addQueryParameter(query.key, queryValue)
                }
            }
        }

        val url = urlBuilder.build()
        var headers = defaultHeaders
        if (requestConfig.headers.count() > 0) {
            headers = requestConfig.headers
        }

        if (headers[Accept] ?: "" == "") {
            throw IllegalStateException("Missing Accept header. This is required.")
        }

        val accept = (headers[Accept] as String).substringBefore(";").toLowerCase()

        var request: Request.Builder = when (requestConfig.method) {
            RequestMethod.DELETE -> Request.Builder().url(url).delete()
            RequestMethod.GET -> Request.Builder().url(url)
            RequestMethod.HEAD -> Request.Builder().url(url).head()
            RequestMethod.OPTIONS -> Request.Builder().url(url).method("OPTIONS", null)
        }

        for (header in headers) {
            request = request.addHeader(header.key, header.value)
        }

        val realRequest = request.build()
        val response = client.newCall(realRequest).execute()

        when {
            response.isSuccessful -> return Success(
                responseBody(response.body, accept),
                response.code,
                response.headers.toMultimap()
            )
            response.isRedirect -> return Redirection(
                response.code,
                response.headers.toMultimap()
            )
            response.code in 100..199 -> return Informational(
                response.message,
                response.code,
                response.headers.toMultimap()
            )
            response.code in 400..499 -> return ClientError(
                response.body?.string(),
                response.code,
                response.headers.toMultimap()
            )
            else -> return ServerError(
                null,
                response.body?.string(),
                response.code,
                response.headers.toMultimap()
            )
        }
    }
}