package com.qucoon.rubiesnigeria.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json


class NetworkProvider {
    var client = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }

        defaultRequest {
            header("Content-Type", "application/json")
            header("Authorization","Bearer 6746754575546")
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
    }

    val socketClient = HttpClient(CIO) {
        install(WebSockets)
        install(Logging) {
            level = LogLevel.ALL
        }
    }
}

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = false
}