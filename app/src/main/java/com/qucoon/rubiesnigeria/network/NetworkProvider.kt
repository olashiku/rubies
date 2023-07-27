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

    val socketClient = HttpClient(CIO) {
        install(WebSockets)
        install(Logging)

//        defaultRequest {
//            header("Authorization",paperPref.getStringPref(PaperPrefs.AUTHORIZATION_TOKEN))
//        }
    }
}

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = false
}