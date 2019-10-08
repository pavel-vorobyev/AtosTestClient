package com.pavelvorobyev.atostest.view.reserve

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import com.pavelvorobyev.atostest.businesslogic.pojo.Reservation
import com.pavelvorobyev.atostest.businesslogic.pojo.ReservationBody
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.businesslogic.repository.ReservationRepository
import com.pavelvorobyev.atostest.businesslogic.util.DateUtils
import com.pavelvorobyev.atostest.di.component.ViewModelComponent
import com.pavelvorobyev.atostest.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ReserveViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: ReservationRepository

    val response = MutableLiveData<RepositoryResponse<Reservation>>()
    val dataValid = MutableLiveData<Boolean>()

    private var roomId = -1
    private var startTime = Calendar.getInstance()
    private var endTime = Calendar.getInstance()

    private var dateValid = false
    private var startTimeValid = false
    private var endTimeValid = false

    override fun handleIntent(intent: Intent?) {
        super.handleIntent(intent)
        roomId = intent?.getIntExtra("room_id", -1)!!
    }

    fun consumeDate(day: Int, month: Int, year: Int) {
        startTime.set(Calendar.DAY_OF_MONTH, day)
        startTime.set(Calendar.MONTH, month)
        startTime.set(Calendar.YEAR, year)

        endTime.set(Calendar.DAY_OF_MONTH, day)
        endTime.set(Calendar.MONTH, month)
        endTime.set(Calendar.YEAR, year)

        dateValid = true
        setDataState()
    }

    fun consumeStartTime(hour: Int, minute: Int) {
        startTime.set(Calendar.HOUR_OF_DAY, hour)
        startTime.set(Calendar.MINUTE, minute)

        startTimeValid = true
        setDataState()
    }

    fun consumeEndTime(hour: Int, minute: Int) {
        endTime.set(Calendar.HOUR_OF_DAY, hour)
        endTime.set(Calendar.MINUTE, minute)

        endTimeValid = true
        setDataState()
    }

    fun getHumanDate(): String {
        return DateUtils.getDateFromTimestamp((startTime.timeInMillis / 1000).toInt())
    }

    fun getHumanStartTime(): String {
        return DateUtils.getTimeFromTimestamp((startTime.timeInMillis / 1000).toInt())
    }

    fun getHumanEndTime(): String {
        return DateUtils.getTimeFromTimestamp((endTime.timeInMillis / 1000).toInt())
    }

    fun reserve() {
        if (!isNetworksAvailable()) {
            Toast.makeText(context, context.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT).show()
            return
        }

        val startTime = (this.startTime.timeInMillis / 1000).toInt()
        val endTime = (this.endTime.timeInMillis / 1000).toInt()
        val body = ReservationBody(roomId, startTime, endTime)

        disposable.add(repository.addReservation(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                response.value = RepositoryResponse.loading()
            }
            .subscribe(
                { r -> handleResponse(r.data) },
                { t -> handleError(getApiErrorFromThrowable(t)) }
            ))
    }

    private fun setDataState() {
        dataValid.value = dateValid && startTimeValid && endTimeValid
    }

    private fun handleResponse(r: Reservation) {
        response.value = RepositoryResponse.success(r)
    }

    private fun handleError(e: ApiError?) {
        response.value = RepositoryResponse.error(e)
    }

    override fun inject(component: ViewModelComponent) {
        component.inject(this)
    }
}