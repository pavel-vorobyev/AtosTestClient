package com.pavelvorobyev.atostest.view.room

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.pavelvorobyev.atostest.businesslogic.pojo.Room
import com.pavelvorobyev.atostest.di.component.ViewModelComponent
import com.pavelvorobyev.atostest.view.base.BaseViewModel

class RoomViewModel(application: Application) : BaseViewModel(application) {

    val room = MutableLiveData<Room>()

    override fun handleIntent(intent: Intent?) {
        val room = intent?.getSerializableExtra("room") as Room
        this.room.value = room
    }

    override fun inject(component: ViewModelComponent) {
        component.inject(this)
    }
}