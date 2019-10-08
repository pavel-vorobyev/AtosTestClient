package com.pavelvorobyev.atostest.businesslogic.db

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.pavelvorobyev.atostest.businesslogic.pojo.Reservation
import java.util.*


class ReservationListTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Reservation> {
        if (data == null)
            return Collections.emptyList()

        val listType = object : TypeToken<List<Reservation>>() { }.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Reservation>): String {
        return gson.toJson(someObjects)
    }

}