package com.qucoon.rubiesnigeria.di_modules

import com.qucoon.rubiesnigeria.repository.rest.ContactsRepository
import com.qucoon.rubiesnigeria.repository.rest.ContactsRepositoryImpl
import com.qucoon.rubiesnigeria.repository.rest.WorkerRepository
import com.qucoon.rubiesnigeria.repository.rest.WorkerRepositoryImpl
import com.qucoon.rubiesnigeria.repository.socket.SocketRepository
import com.qucoon.rubiesnigeria.repository.socket.SocketRepositoryImpl
import org.koin.dsl.module

val repository = module{
    single <SocketRepository>{ SocketRepositoryImpl(networkProvider = get()) }
    factory<ContactsRepository>{ ContactsRepositoryImpl(appContext = get(), contactsDataSource = get()) }
    factory <WorkerRepository>{ WorkerRepositoryImpl() }
}