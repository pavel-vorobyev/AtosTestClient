package com.pavelvorobyev.atostest.businesslogic.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (

    @SerializedName("u_id")
    val u_id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String

): Serializable