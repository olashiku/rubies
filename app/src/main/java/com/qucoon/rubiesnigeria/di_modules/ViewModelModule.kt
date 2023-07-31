package com.qucoon.rubiesnigeria.di_modules

import com.qucoon.rubiesnigeria.base.BaseSocketViewModel
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import com.qucoon.rubiesnigeria.viewmodel.ChatViewModel
import com.qucoon.rubiesnigeria.viewmodel.RestAuthViewModel
import com.qucoon.rubiesnigeria.viewmodel.ScarletResponseViewModel
import com.qucoon.rubiesnigeria.viewmodel.ScarletSocketViewModel
import com.qucoon.rubiesnigeria.viewmodel.SocketAuthenticationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module  {
    single { AuthViewModel(socketRepository = get(), contactsDataSource = get(), chatsDataSource = get()) }
    single { ChatViewModel(dataSource = get(), socketRepository = get(),chatsDataSource = get()) }
    single { ScarletSocketViewModel(scarletSocketRepository = get()) }
    single { SocketAuthenticationViewModel(scarletSocketRepository = get(),chatsDataSource = get()) }
    single { ScarletResponseViewModel(scarletSocketRepository = get()) }
    single { RestAuthViewModel(authRepository = get()) }



}