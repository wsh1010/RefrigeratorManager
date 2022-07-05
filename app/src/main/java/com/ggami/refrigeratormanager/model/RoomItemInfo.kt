package com.ggami.refrigeratormanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class RoomItemInfo (
    @ColumnInfo var itemName: String,
    @ColumnInfo var itemCount: Int,
    @ColumnInfo var itemCategory: Int,
    @ColumnInfo var itemExpiredDate: LocalDate,
    ) {
    @PrimaryKey(autoGenerate = true) var itemId: Long = 0
}
