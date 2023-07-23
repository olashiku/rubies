package com.qucoon.rubiesnigeria.di_modules

import com.qucoon.rubiesnigeria.network.NetworkProvider
import org.koin.dsl.module

val network = module {
    single { NetworkProvider() }
}