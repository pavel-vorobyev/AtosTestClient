package com.pavelvorobyev.atostest.businesslogic.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pavelvorobyev.atostest.businesslogic.pojo.Room
import io.reactivex.Flowable

@Dao
interface RoomDao {

    @Insert
    fun insert(room: Room)

    @Query("SELECT * FROM room_table ORDER BY id DESC")
    fun getRooms(): Flowable<List<Room>>

    @Query("SELECT * FROM room_table ORDER BY id DESC")
    fun getRoomsSync(): List<Room>

    @Query("DELETE FROM room_table WHERE id = :id")
    fun delete(id: Int)

}