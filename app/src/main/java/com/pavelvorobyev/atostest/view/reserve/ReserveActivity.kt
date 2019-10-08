package com.pavelvorobyev.atostest.view.reserve

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import com.pavelvorobyev.atostest.businesslogic.pojo.Reservation
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.businesslogic.repository.Status
import com.pavelvorobyev.atostest.view.base.BaseActivity
import com.pavelvorobyev.atostest.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_reserve.*
import java.util.Calendar

class ReserveActivity : BaseActivity<ReserveViewModel>(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener{

    companion object {

        fun start(context: Context, roomId: Int) {
            val intent = Intent(context, ReserveActivity::class.java).apply {
                putExtra("room_id", roomId)
            }
            context.startActivity(intent)
        }

    }

    private val calendar = Calendar.getInstance()

    private lateinit var datePicker: DatePickerDialog
    private lateinit var timePicker: TimePickerDialog
    private lateinit var successDialog: AlertDialog
    private lateinit var errorDialog: AlertDialog
    private var timeType = TimeTypes.START_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActionBarTitle(getString(R.string.reserve))
        enableBackButton()

        viewModel.handleIntent(intent)

        viewModel.dataValid.observe(this,
            Observer {
                reserveBtnView.isEnabled = it
            })

        viewModel.response.observe(this,
            Observer {
                consumeResponse(it)
            })

        datePicker = DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        timePicker = TimePickerDialog(this, this, calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE), true)

        dateView.setOnClickListener {
            datePicker.datePicker.minDate = System.currentTimeMillis()
            datePicker.show()
        }

        startTimeView.setOnClickListener {
            timeType = TimeTypes.START_TIME
            timePicker.show()
        }

        endTimeView.setOnClickListener {
            timeType = TimeTypes.END_TIME
            timePicker.show()
        }

        reserveBtnView.setOnClickListener {
            viewModel.reserve()
        }

        successDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.success))
            .setMessage(getString(R.string.your_request_has_been_sent_to_manager))
            .setPositiveButton("OK") { _, _ ->
                MainActivity.start(this)
            }
            .create()

        errorDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setPositiveButton("OK") { _, _ ->

            }
            .create()

    }

    override fun getLayout(): Int {
        return R.layout.activity_reserve
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.consumeDate(dayOfMonth, month, year)
        dateView.text = viewModel.getHumanDate()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        when (timeType) {
            TimeTypes.START_TIME -> {
                viewModel.consumeStartTime(hourOfDay, minute)
                startTimeView.text = viewModel.getHumanStartTime()
            }
            TimeTypes.END_TIME -> {
                viewModel.consumeEndTime(hourOfDay, minute)
                endTimeView.text = viewModel.getHumanEndTime()
            }
        }
    }

    private fun consumeResponse(response: RepositoryResponse<Reservation>) {
        when (response.status) {
            Status.LOADING -> consumeLoading()
            Status.SUCCESS -> consumeSuccess()
            Status.ERROR -> consumeError(response.error)
        }
    }

    private fun consumeLoading() {
        reserveBtnView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun consumeSuccess() {
        progressBar.visibility = View.GONE
        reserveBtnView.visibility = View.VISIBLE
        successDialog.show()
    }

    private fun consumeError(e: ApiError?) {
        progressBar.visibility = View.GONE
        reserveBtnView.visibility = View.VISIBLE

        when {
            e?.pointer == "end_time" && e.reason == "INVALID" -> {
                errorDialog.setMessage(getString(R.string.time_is_invalid))
                errorDialog.show()
            }
            e?.pointer == "request" && e.reason == "CREATED" -> {
                errorDialog.setMessage(getString(R.string.reservation_already_requested))
                errorDialog.show()
            }
            e?.pointer == "start_time" && e.reason == "TAKEN" -> {
                errorDialog.setMessage(getString(R.string.time_is_taken))
                errorDialog.show()
            }
        }
    }

    private enum class TimeTypes {
        START_TIME,
        END_TIME
    }

}