package com.pavelvorobyev.atostest.businesslogic.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "authorization_table")
data class Authorization (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("token")
    @ColumnInfo(name = "token")
    val token: String,

    @SerializedName("user")
    @ColumnInfo(name = "user")
    val user: User

): Serializable