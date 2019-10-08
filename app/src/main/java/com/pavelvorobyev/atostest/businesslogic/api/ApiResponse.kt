package com.pavelvorobyev.atostest.businesslogic.api

import com.google.gson.annotations.SerializedName

class ApiResponse<D> (

    @SerializedName("data")
    val data: D

)