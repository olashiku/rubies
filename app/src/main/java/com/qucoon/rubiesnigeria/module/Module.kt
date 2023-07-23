package com.qucoon.rubiesnigeria.module

import androidx.multidex.MultiDexApplication
import com.qucoon.rubiesnigeria.di_modules.network
import com.qucoon.rubiesnigeria.di_modules.repository
import com.qucoon.rubiesnigeria.di_modules.storage
import com.qucoon.rubiesnigeria.di_modules.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Module: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

     private fun setupKoin(){
       startKoin {
           androidContext(this@Module)
           modules(listOf(network, storage,repository, viewModel))
       }
     }
}