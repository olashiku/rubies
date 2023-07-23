package com.qucoon.rubiesnigeria.network

import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
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
import org.koin.java.KoinJavaComponent


class NetworkProvider {

    val paperPref: PaperPrefs by KoinJavaComponent.inject(PaperPrefs::class.java)

    var client = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }

        defaultRequest {
            header("Content-Type", "application/json")
            header("Authorization",paperPref.getStringPref(PaperPrefs.AUTHORIZATION_TOKEN))
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
    }

    val socketClient = HttpClient(CIO) {
        install(WebSockets){
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        defaultRequest {
            header("Authorization",paperPref.getStringPref(PaperPrefs.AUTHORIZATION_TOKEN))
        }
    }
}

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = false
}