package com.pavelvorobyev.atostest.businesslogic.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "room_table")
data class Room (

    @SerializedName("r_id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,

    @SerializedName("with_projector")
    @ColumnInfo(name = "with_projector")
    val withProjector: Boolean,

    @SerializedName("with_board")
    @ColumnInfo(name = "with_board")
    val withBoard: Boolean,

    @SerializedName("seats_count")
    @ColumnInfo(name = "seats_count")
    val seatsCount: Int,

    @SerializedName("reservations")
    @ColumnInfo(name = "reservations")
    val reservations: List<Reservation>

): Serializable