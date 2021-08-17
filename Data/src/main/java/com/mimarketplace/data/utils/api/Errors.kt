@file:Suppress("unused")
package com.mimarketplace.data.utils.api

import java.lang.RuntimeException

open class ClientException : RuntimeException {

    /**
     * Constructs an [ClientException] with no detail message.
     */
    constructor() : super()

    /**
     * Constructs an [ClientException] with the specified detail message.

     * @param   message   the detail message.
     */
    constructor(message: kotlin.String) : super(message)

    companion object {
        private const val serialVersionUID: Long = 123L
    }
}

open class ServerException : RuntimeException {

    /**
     * Constructs an [ServerException] with no detail message.
     */
    constructor() : super()

    /**
     * Constructs an [ServerException] with the specified detail message.

     * @param   message   the detail message.
     */
    constructor(message: kotlin.String) : super(message)

    companion object {
        private const val serialVersionUID: Long = 456L
    }
}

open class AuthenticationException : RuntimeException {

    /**
     * Constructs an [AuthenticationException] with no detail message.
     */
    constructor() : super()

    /**
     * Constructs an [AuthenticationException] with the specified detail message.

     * @param   message   the detail message.
     */
    constructor(message: kotlin.String) : super(message)

    companion object {
        private const val serialVersionUID: Long = 656L
    }
}