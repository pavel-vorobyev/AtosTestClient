package com.pavelvorobyev.atostest.view.main

import android.app.Application
import android.content.Context
import com.pavelvorobyev.atostest.businesslogic.util.NotificationsService
import com.pavelvorobyev.atostest.di.component.ViewModelComponent
import com.pavelvorobyev.atostest.view.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    fun notificationService() {
        if (!NotificationsService.isRunning(context)) {
            NotificationsService.run(context)
        }
    }

    override fun inject(component: ViewModelComponent) {
        component.inject(this)
    }

}