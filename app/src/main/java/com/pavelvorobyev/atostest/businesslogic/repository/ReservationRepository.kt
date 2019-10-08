package com.pavelvorobyev.atostest.businesslogic.repository

import com.pavelvorobyev.atostest.businesslogic.api.ApiHelper
import com.pavelvorobyev.atostest.businesslogic.api.ApiResponse
import com.pavelvorobyev.atostest.businesslogic.pojo.Reservation
import com.pavelvorobyev.atostest.businesslogic.pojo.ReservationBody
import io.reactivex.Observable
import javax.inject.Inject

class ReservationRepository
@Inject
constructor(private val apiHelper: ApiHelper) {

    fun addReservation(reservation: ReservationBody): Observable<ApiResponse<Reservation>> {
        return apiHelper.reservationService.addReservation(reservation)
    }

}