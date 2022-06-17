package com.ggami.refrigeratormanager

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.time.LocalDate

class RefrigeratorData {
    enum class Category(val Name: String){
        CT0("기타"),
        CT1("육류/가금류"),
        CT2("달걀/우유"),
        CT3("생선"),
        CT4("채소"),
        CT5("과일"),
        CT6("냉동식품"),
    }

    data class ItemInfo(
            var name: String = "",
            var count: Int = 1,
            var category: Category = Category.CT0, // 분류 종목
            //var savePlace: Int = 1, // 1, 2, 3, 4
            var expiredDate: LocalDate = LocalDate.now().plusWeeks(1),
            var selected: Boolean = false
    )

    data class RefrigeratorInfo(
            var id: Int = 2, // 1: 1door, 2: 2door(|), 3: 2door(-), 4: 4door
            var itemRefrigerator: MutableMap<Int, MutableList<ItemInfo>> = mutableMapOf()
            //var itemList: MutableList<ItemInfo> = mutableListOf(),
    )

    companion object {
        var refriInfo = RefrigeratorInfo()
        private const val fileInfoName = "RefriInfo"

        fun init() {
            refriInfo.itemRefrigerator[1] = mutableListOf<ItemInfo>()
            refriInfo.itemRefrigerator[2] = mutableListOf<ItemInfo>()
            refriInfo.itemRefrigerator[3] = mutableListOf<ItemInfo>()
            refriInfo.itemRefrigerator[4] = mutableListOf<ItemInfo>()
            refriInfo.id = 2
        }
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

        fun getCategory(ordinal: Int) : Category{
            return when(ordinal){
                0-> Category.CT0
                1-> Category.CT1
                2-> Category.CT2
                3-> Category.CT3
                4-> Category.CT4
                5-> Category.CT5
                6-> Category.CT6
                else -> Category.CT0
            }
        }

        fun addItemsRefrigeratorToDoor(saveDoor: Int, items: MutableList<ItemInfo>) {
            if (saveDoor in 1..4) {
                refriInfo.itemRefrigerator[saveDoor]!!.addAll(items)
            }
        }

        fun deleteRefrigeratorFromDoor(saveDoor: Int, position: Int) {
            if(saveDoor in 1..4) {
                refriInfo.itemRefrigerator[saveDoor]!!.removeAt(position)
            }
        }

    }
}