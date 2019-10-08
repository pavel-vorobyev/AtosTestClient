package com.pavelvorobyev.atostest.view.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.pavelvorobyev.atostest.businesslogic.repository.AuthorizationRepository
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.di.component.ViewModelComponent
import com.pavelvorobyev.atostest.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: AuthorizationRepository

    val response = MutableLiveData<RepositoryResponse<Int>>()

    fun getAuthorizations() {
        disposable.add(repository.getAuthorizationsFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { r -> handleResponse(r.size) },
                { handleError() }
            ))
    }

    private fun handleResponse(i: Int) {
        response.value = RepositoryResponse.success(i)
    }

    private fun handleError() {
        response.value = RepositoryResponse.error(null)
    }

    override fun inject(component: ViewModelComponent) {
        component.inject(this)
    }
}