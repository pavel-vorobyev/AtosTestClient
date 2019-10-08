package com.pavelvorobyev.atostest.businesslogic.db.authorization

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pavelvorobyev.atostest.businesslogic.db.UserTypeConverter
import com.pavelvorobyev.atostest.businesslogic.pojo.Authorization

@Database(entities = [Authorization::class], version = 2)
@TypeConverters(UserTypeConverter::class)
abstract class AuthorizationDb : RoomDatabase() {

    companion object {

        fun getInstance(context: Context): AuthorizationDb {
            return androidx.room.Room.databaseBuilder(context, AuthorizationDb::class.java,
                "authorization_db")
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract fun authorizationDao(): AuthorizationDao

}