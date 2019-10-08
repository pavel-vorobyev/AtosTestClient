package com.pavelvorobyev.atostest.businesslogic.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReservationBody (

    @SerializedName("room_id")
    val roomId: Int,

    @SerializedName("start_time")
    val startTime: Int,

    @SerializedName("end_time")
    val endTime: Int

): Serializable