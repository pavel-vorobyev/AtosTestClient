package com.pavelvorobyev.atostest.businesslogic.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pavelvorobyev.atostest.businesslogic.db.ReservationListTypeConverter
import com.pavelvorobyev.atostest.businesslogic.pojo.Room

@Database(entities = [Room::class], version = 3)
@TypeConverters(ReservationListTypeConverter::class)
abstract class RoomDB: RoomDatabase() {

    companion object {

        fun getInstance(context: Context): RoomDB {
            return androidx.room.Room.databaseBuilder(context, RoomDB::class.java,
                "room_db")
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract fun roomDao(): RoomDao

}