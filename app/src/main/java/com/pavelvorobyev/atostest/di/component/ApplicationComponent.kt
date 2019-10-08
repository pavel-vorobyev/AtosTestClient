package com.pavelvorobyev.atostest.di.component

import android.app.Application
import com.pavelvorobyev.atostest.di.module.ApplicationModule
import com.pavelvorobyev.atostest.di.module.ServiceModule
import com.pavelvorobyev.atostest.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(target: Application)
    fun plus(viewModelModule: ViewModelModule): ViewModelComponent
    fun plus(serviceModule: ServiceModule): ServiceComponent
}