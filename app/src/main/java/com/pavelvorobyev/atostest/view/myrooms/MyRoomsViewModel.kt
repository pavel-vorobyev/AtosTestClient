package com.pavelvorobyev.atostest.view.myrooms

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import com.pavelvorobyev.atostest.businesslogic.api.ApiResponse
import com.pavelvorobyev.atostest.businesslogic.pojo.Room
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.businesslogic.repository.RoomRepository
import com.pavelvorobyev.atostest.di.component.ViewModelComponent
import com.pavelvorobyev.atostest.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MyRoomsViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: RoomRepository

    val response = MutableLiveData<RepositoryResponse<List<Room>>>()

    override fun inject(component: ViewModelComponent) {
        component.inject(this)
    }

    fun getMyRooms() {
        if (!isNetworksAvailable()) {
            Toast.makeText(context, context.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT).show()
            return
        }

        disposable.add(repository.getMyRooms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                response.value = RepositoryResponse.loading()
            }
            .doOnError {

            }
            .subscribe(
                { r -> handleResponse(r.data) },
                { t -> handleError(getApiErrorFromThrowable(t)) }
            ))
    }

    private fun handleResponse(r: List<Room>) {
        response.value = RepositoryResponse.success(r)
    }

    private fun handleError(e: ApiError?) {
        response.value = RepositoryResponse.error(e)
    }

}