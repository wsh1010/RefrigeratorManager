package com.ggami.refrigeratormanager

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.time.LocalDate

class RefrigeratorData {
    data class ItemInfo(
            var name: String = "",
            var count: Int = 1,
            var category: String = "기타", // 분류 종목
            var savePlace: Int = 1, // 1, 2, 3, 4
            var expiredDate: LocalDate = LocalDate.of(9999, 12, 31),
    )

    data class RefrigeratorInfo(
            var id: Int = 2, // 1: 1door, 2: 2door(|), 3: 2door(-), 4: 4door
            var itemList: MutableList<ItemInfo> = mutableListOf(),
    )

    companion object {
        var refriInfo = RefrigeratorInfo()
        private const val fileInfoName = "RefriInfo"
        fun saveRefriInfo(context: Context) {
            val fileContents = Gson().toJson(refriInfo)
            val file = File(context.filesDir, fileInfoName)
            file.writeText(fileContents)
        }
        fun readRefriInfo(context: Context): Boolean {
            val file =File(context.filesDir, fileInfoName)
            if(file.exists()) {
                refriInfo = Gson().fromJson(file.readText(), RefrigeratorInfo::class.java)
                return true
            }
            return false
        }

    }
}