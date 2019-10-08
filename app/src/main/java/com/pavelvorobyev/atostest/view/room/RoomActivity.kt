package com.pavelvorobyev.atostest.view.room

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.pojo.Reservation
import com.pavelvorobyev.atostest.businesslogic.pojo.Room
import com.pavelvorobyev.atostest.businesslogic.util.DateUtils
import com.pavelvorobyev.atostest.view.base.BaseActivity
import com.pavelvorobyev.atostest.view.reserve.ReserveActivity
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.view_item_reservation.view.*
import java.lang.Exception

class RoomActivity : BaseActivity<RoomViewModel>() {

    companion object {

        fun start(context: Context, room: Room?) {
            val intent = Intent(context, RoomActivity::class.java).apply {
                putExtra("room", room)
            }
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableBackButton()

        viewModel.room.observe(this,
                Observer {
                    setRoomInfo(it)
                })

        viewModel.handleIntent(intent)

        reserveBtnView.setOnClickListener {
            ReserveActivity.start(this, viewModel.room.value?.id!!)
        }


    }

    override fun getLayout(): Int {
        return R.layout.activity_room
    }

    private fun setRoomInfo(room: Room?) {
        setActionBarTitle(room?.name.toString())

        seatsCountView.text = resources.getString(R.string.there_seats_in_the_room)
                .format(room?.seatsCount)

        featuresContainerView.visibility =
                when (!room?.withBoard!! && !room.withProjector) {
                    true -> View.GONE
                    false -> View.VISIBLE
                }

        hasProjectorView.visibility = when (room.withProjector) {
            true -> View.VISIBLE
            false -> View.GONE
        }

        hasBoardView.visibility = when (room.withBoard) {
            true -> View.VISIBLE
            false -> View.GONE
        }

        descriptionView.text = room.description

        setRoomReservations(room.reservations)
    }

    @SuppressLint("SetTextI18n")
    private fun setRoomReservations(reservations: List<Reservation>) {
        if (reservations.isEmpty()) {
            reservedTimeTitleView.visibility = View.GONE
            reservedTimeContainerView.visibility = View.GONE
            return
        }

        reservedTimeTitleView.visibility = View.VISIBLE
        reservedTimeContainerView.visibility = View.VISIBLE
        reservedTimeContainerView.removeAllViews()

        for (reservation in reservations) {
            val reservationView = LayoutInflater.from(this)
                    .inflate(R.layout.view_item_reservation, reservedTimeContainerView, false)

            reservationView.dateView.text = DateUtils.getDateFromTimestamp(reservation.startTime)
            reservationView.timeView.text = "${DateUtils.getTimeFromTimestamp(reservation.startTime)}-" +
                    DateUtils.getTimeFromTimestamp(reservation.endTime)
            reservationView.nameView.text = reservation.user?.name

            reservedTimeContainerView.addView(reservationView)
        }
    }
}