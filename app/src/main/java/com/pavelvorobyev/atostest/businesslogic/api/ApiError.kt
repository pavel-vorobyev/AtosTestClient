package com.pavelvorobyev.atostest.businesslogic.api

import com.google.gson.annotations.SerializedName

data class ApiError (

    @SerializedName("pointer")
    val pointer: String,
    @SerializedName("reason")
    val reason: String,
    var code: Int = 0

)