package com.qucoon.rubiesnigeria.di_modules

import androidx.room.Room
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.room.data_source.*
import com.qucoon.rubiesnigeria.storage.room.engine.DatabaseEngine
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storage = module {
    single { PaperPrefs(context = get()) }
    single { Room.databaseBuilder(androidApplication(),DatabaseEngine::class.java,"RubiesDb").fallbackToDestructiveMigration().build() }
    single(createdAtStart = false){get<DatabaseEngine>().contactsDao()}
    single(createdAtStart = false){get<DatabaseEngine>().chatDao()}
    single(createdAtStart = false){get<DatabaseEngine>().friendsDao()}


    // data sources
    factory <ContactsDataSource>{ ContactsDataSourceImpl(contactsDao = get()) }
    factory <ChatsDataSource>{ ChatsDataSourceImpl(chatDao = get()) }
    factory <FriendsDataSource>{ FriendsDataSourceImpl(friendsDao = get()) }
}