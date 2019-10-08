package com.pavelvorobyev.atostest.businesslogic.api

import com.pavelvorobyev.atostest.BuildConfig
import com.pavelvorobyev.atostest.businesslogic.pojo.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiServices {

    interface UserService {

        @POST(BuildConfig.API_VERSION + "/user")
        fun signIn(@Body body: SignInBody): Observable<ApiResponse<Authorization>>

    }

    interface RoomService {

        @GET(BuildConfig.API_VERSION + "/room")
        fun getRooms(@Query("type") type: String): Observable<ApiResponse<List<Room>>>

    }

    interface ReservationService {

        @PUT(BuildConfig.API_VERSION + "/reservation")
        fun addReservation(@Body body: ReservationBody): Observable<ApiResponse<Reservation>>

    }

}