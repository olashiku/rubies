package com.qucoon.rubiesnigeria.storage.room.data_source


import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.storage.room.dao.FriendsDao
import kotlinx.coroutines.flow.Flow

interface FriendsDataSource {
    suspend fun updateFriends(listOfFriends: List<Friend>)
    fun getAllFriends(): Flow<List<Friend>>
}

class FriendsDataSourceImpl(private val friendsDao: FriendsDao): FriendsDataSource {
    override suspend fun updateFriends(listOfFriends: List<Friend>) {
        friendsDao.updateFriends(listOfFriends)
    }
    override fun getAllFriends(): Flow<List<Friend>> {
        return friendsDao.getAllFriends()
    }

}