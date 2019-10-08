package com.pavelvorobyev.atostest.businesslogic.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Reservation (

    @SerializedName("r_id")
    val id: Int?,

    @SerializedName("room")
    val roomId: Room,

    @SerializedName("user")
    val user: User?,

    @SerializedName("start_time")
    val startTime: Int,

    @SerializedName("end_time")
    val endTime: Int,

    @SerializedName("status")
    val status: String

): Serializable