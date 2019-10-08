package com.pavelvorobyev.atostest

import android.app.Application
import com.pavelvorobyev.atostest.businesslogic.api.ApiHelper
import com.pavelvorobyev.atostest.businesslogic.util.NotificationsManager
import com.pavelvorobyev.atostest.di.component.ApplicationComponent
import com.pavelvorobyev.atostest.di.component.DaggerApplicationComponent
import com.pavelvorobyev.atostest.di.module.ApplicationModule
import javax.inject.Inject

class AtosTest : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        NotificationsManager.createNotificationChannel(applicationContext)
    }

}