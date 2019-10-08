package com.pavelvorobyev.atostest.di.component

import com.pavelvorobyev.atostest.businesslogic.util.NotificationsService
import com.pavelvorobyev.atostest.di.module.ServiceModule
import com.pavelvorobyev.atostest.di.scope.ServiceScope
import dagger.Subcomponent

@ServiceScope
@Subcomponent(modules = [ServiceModule::class])
interface ServiceComponent {

    fun inject(target: NotificationsService)

}