package com.pavelvorobyev.atostest.view.signin

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.pavelvorobyev.atostest.R
import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import com.pavelvorobyev.atostest.businesslogic.pojo.Authorization
import com.pavelvorobyev.atostest.businesslogic.pojo.SignInBody
import com.pavelvorobyev.atostest.businesslogic.repository.AuthorizationRepository
import com.pavelvorobyev.atostest.businesslogic.repository.RepositoryResponse
import com.pavelvorobyev.atostest.di.component.ViewModelComponent
import com.pavelvorobyev.atostest.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var authorizationRepository: AuthorizationRepository

    val response = MutableLiveData<RepositoryResponse<Authorization>>()

    private var username = ""
    private var password = ""

    fun onUsername(s: String) {
        username = s.trim()
    }

    fun onPasswod(s: String) {
        password = s.trim()
    }

    fun signIn() {
        if (!isNetworksAvailable()) {
            Toast.makeText(context, context.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT).show()
            return
        }

        val body = SignInBody(username, password)

        disposable.add(authorizationRepository.signIn(body)
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

    private fun handleResponse(r: Authorization) {
        response.value = RepositoryResponse.success(r)
        authorizationRepository.insetAuthorization(r)
    }

    private fun handleError(e: ApiError?) {
        response.value = RepositoryResponse.error(e)
    }

    override fun inject(component: ViewModelComponent) {
        component.inject(this)
    }
}