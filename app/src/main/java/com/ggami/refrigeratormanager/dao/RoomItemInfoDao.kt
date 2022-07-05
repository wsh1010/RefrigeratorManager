package com.ggami.refrigeratormanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ggami.refrigeratormanager.model.RoomItemInfo

@Dao
interface RoomItemInfoDao {
    @Query("SELECT * FROM RoomItemInfo")
    fun getItemInfoList() : List<RoomItemInfo>

    @Insert
    fun insertItemInfoList(vararg item: RoomItemInfo): List<Long>

    @Query("DELETE FROM RoomItemInfo WHERE itemId = :id")
    fun deleteById(id: Long)
}