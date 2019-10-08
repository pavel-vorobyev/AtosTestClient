package com.pavelvorobyev.atostest.businesslogic.repository

import android.annotation.SuppressLint
import com.pavelvorobyev.atostest.businesslogic.api.ApiHelper
import com.pavelvorobyev.atostest.businesslogic.api.ApiResponse
import com.pavelvorobyev.atostest.businesslogic.pojo.Room
import com.pavelvorobyev.atostest.businesslogic.db.room.RoomDao
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import kotlin.concurrent.thread

@SuppressLint("StaticFieldLeak")
class RoomRepository
@Inject
constructor(private val apiHelper: ApiHelper,
            private val roomDao: RoomDao
) {

    fun getAvailableRooms(): Observable<ApiResponse<List<Room>>> {
        return apiHelper.roomService
            .getRooms("available")
    }

    fun getMyRooms(): Observable<ApiResponse<List<Room>>> {
        return apiHelper.roomService
            .getRooms("my")
    }

    fun getRoomsFromCache(): Flowable<List<Room>> {
        return roomDao.getRooms()
    }

    fun cacheRooms(rooms: List<Room>) {
        thread {
            for (room in rooms)
                insertRoomIntoDb(room)
        }
    }

    private fun insertRoomIntoDb(room: Room) {
        thread {
            roomDao.delete(room.id)
            roomDao.insert(room)
        }
    }

}