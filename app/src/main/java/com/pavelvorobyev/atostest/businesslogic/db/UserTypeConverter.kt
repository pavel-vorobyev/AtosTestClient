package com.pavelvorobyev.atostest.businesslogic.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pavelvorobyev.atostest.businesslogic.pojo.User

class UserTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToObject(data: String?): User {
        return gson.fromJson(data, User::class.java)
    }

    @TypeConverter
    fun objectToString(data: User): String {
        return gson.toJson(data)
    }

}