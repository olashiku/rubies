package com.qucoon.rubiesnigeria.di_modules

import android.app.Application
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.qucoon.rubiesnigeria.network.NetworkProvider
import com.qucoon.rubiesnigeria.network.RubiesApi
import com.qucoon.rubiesnigeria.repository.socket.ScarletSocket
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.ExponentialBackoffStrategy
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val DEV_BASE_URL = "https://o2x4s7fcyl.execute-api.us-east-1.amazonaws.com/dev/"

val network = module {

    single {
        createWebService<RubiesApi>(
            RxJava2CallAdapterFactory.create(),
            DEV_BASE_URL,get())

    }
    single { NetworkProvider() }
    single {
        createSocket<ScarletSocket>(okHttpClient(get()),androidApplication())
    }

}

inline fun <reified T> createWebService(
    factory: CallAdapter.Factory, baseUrl: String, paperPrefs: PaperPrefs
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient(paperPrefs))
        .build()
    return retrofit.create(T::class.java)
}

inline fun <reified T>  createSocket (okHttpClient: OkHttpClient, application: Application) : T {
    val BACKOFF_STRATEGY = LinearBackoffStrategy(3000)
    val scarletInstance = Scarlet.Builder()

        .webSocketFactory(okHttpClient
            .newWebSocketFactory(getRubiesSocketUrl()))
     //   .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .backoffStrategy(BACKOFF_STRATEGY)
        .lifecycle(createAppForegroundAndUserLoggedInLifecycle(application))
        .build()

    return scarletInstance.create(T::class.java)

}

fun getRubiesSocketUrl () :String {
    return "wss://txl4o2wbqe.execute-api.us-east-1.amazonaws.com/production"
}

fun createAppForegroundAndUserLoggedInLifecycle(application: Application): Lifecycle {
    return AndroidLifecycle.ofApplicationForeground(application)
}


fun okHttpClient(paperPrefs: PaperPrefs) =
    OkHttpClient.Builder()
        .addInterceptor(headersInterceptor(paperPrefs))
        .addInterceptor(loggingInterceptor())
        .readTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(  5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .build()

fun headersInterceptor(paperPrefs: PaperPrefs) = Interceptor { chain ->

    chain.proceed(chain.request().newBuilder()
       .addHeader("Content-Type", "application/json")
       .addHeader("Authorization", paperPrefs.getStringPref(PaperPrefs.AUTH_TOKEN))
        .build())

}

val backoffStrategy = ExponentialBackoffStrategy(
    initialDurationMillis = 5000, // Initial duration
    maxDurationMillis = 60000, // Maximum duration
)

fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (com.qucoon.rubiesnigeria.BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}