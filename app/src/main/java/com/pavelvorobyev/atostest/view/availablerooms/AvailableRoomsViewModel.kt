package com.pavelvorobyev.atostest.view.availablerooms

import android.app.Application
import androidx.lifecycle.MutableLiveData
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

class AvailableRoomsViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: RoomRepository

    val response = MutableLiveData<RepositoryResponse<List<Room>>>()

    override fun inject(component: ViewModelComponent) {
        component.inject(this)
    }

    fun getAvailableRooms() {
        if (!isNetworksAvailable()) {
            disposable.add(repository.getRoomsFromCache()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    response.value = RepositoryResponse.loading()
                }
                .subscribe {
                    handleResponse(it, false)
                })
            return
        }

        disposable.add(repository.getAvailableRooms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                response.value = RepositoryResponse.loading()
            }
            .doOnError {

            }
            .subscribe (
                { r -> handleResponse(r.data, true) },
                { t -> handleError(getApiErrorFromThrowable(t)) }
            ))
    }

    private fun handleResponse(r: List<Room>, cache: Boolean) {
        response.value = RepositoryResponse.success(r)

        if (cache)
            repository.cacheRooms(r)
    }

    private fun handleError(e: ApiError?) {
        response.value = RepositoryResponse.error(e)
    }
}