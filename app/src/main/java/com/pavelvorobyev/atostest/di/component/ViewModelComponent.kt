package com.pavelvorobyev.atostest.di.component

import com.pavelvorobyev.atostest.di.module.ViewModelModule
import com.pavelvorobyev.atostest.di.scope.ViewModelScope
import com.pavelvorobyev.atostest.view.availablerooms.AvailableRoomsViewModel
import com.pavelvorobyev.atostest.view.main.MainViewModel
import com.pavelvorobyev.atostest.view.myrooms.MyRoomsViewModel
import com.pavelvorobyev.atostest.view.reserve.ReserveViewModel
import com.pavelvorobyev.atostest.view.room.RoomViewModel
import com.pavelvorobyev.atostest.view.signin.SignInViewModel
import com.pavelvorobyev.atostest.view.splash.SplashViewModel
import dagger.Subcomponent

@ViewModelScope
@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {

    fun inject(target: MainViewModel)
    fun inject(target: MyRoomsViewModel)
    fun inject(target: AvailableRoomsViewModel)
    fun inject(target: RoomViewModel)
    fun inject(target: ReserveViewModel)
    fun inject(target: SignInViewModel)
    fun inject(target: SplashViewModel)

}