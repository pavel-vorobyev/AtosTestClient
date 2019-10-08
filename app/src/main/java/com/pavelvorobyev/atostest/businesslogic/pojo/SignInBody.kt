package com.pavelvorobyev.atostest.businesslogic.pojo

import com.google.gson.annotations.SerializedName

data class SignInBody (

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String

)