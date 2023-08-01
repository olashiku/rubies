package com.qucoon.rubiesnigeria.storage.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.storage.room.dao.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendsDao: BaseDao<Friend> {
    @Query("select * from friends")
    fun getAllFriends(): Flow<List<Friend>>

    @Query("DELETE FROM friends ")
    fun deleteAllFriends()

    @androidx.room.Transaction
    fun updateFriends(friends: List<Friend>) {
        deleteAllFriends()
        insertAll(friends)
    }
}