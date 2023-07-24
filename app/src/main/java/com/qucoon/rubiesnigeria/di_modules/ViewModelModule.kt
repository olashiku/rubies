package com.qucoon.rubiesnigeria.di_modules

import com.qucoon.rubiesnigeria.base.BaseSocketViewModel
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import com.qucoon.rubiesnigeria.viewmodel.ChatViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module  {
    single { AuthViewModel(socketRepository = get(), contactsDataSource = get()) }
    single { ChatViewModel(dataSource = get(), socketRepository = get()) }
}