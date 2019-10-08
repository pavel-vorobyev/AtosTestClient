package com.pavelvorobyev.atostest.view.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.pavelvorobyev.atostest.AtosTest
import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import com.pavelvorobyev.atostest.businesslogic.util.ApiErrorParser
import com.pavelvorobyev.atostest.di.component.ViewModelComponent
import com.pavelvorobyev.atostest.di.module.ViewModelModule
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected lateinit var context: Context
    protected val disposable = CompositeDisposable()

    private var component: ViewModelComponent =
        (application as AtosTest).applicationComponent
            .plus(ViewModelModule())

    fun initDI() {
        inject(component)
        context = getApplication()
    }

    protected fun getApiErrorFromThrowable(t: Throwable): ApiError? {
        return try {
            val httpException = t as HttpException
            ApiErrorParser
                .parse(httpException.response()?.errorBody()?.string().toString(), t.code())
        }catch (e: Exception) {
            null
        }
    }

    protected fun isNetworksAvailable(): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnected ?: false
    }

    open fun handleIntent(intent: Intent?) {

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    abstract fun inject(component: ViewModelComponent)

}