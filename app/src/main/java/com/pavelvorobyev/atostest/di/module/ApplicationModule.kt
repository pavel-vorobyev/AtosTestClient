package com.pavelvorobyev.atostest.di.module

import android.app.Application
import com.pavelvorobyev.atostest.businesslogic.api.ApiHelper
import com.pavelvorobyev.atostest.businesslogic.db.authorization.AuthorizationDb
import com.pavelvorobyev.atostest.businesslogic.repository.ReservationRepository
import com.pavelvorobyev.atostest.businesslogic.repository.RoomRepository
import com.pavelvorobyev.atostest.businesslogic.db.room.RoomDB
import com.pavelvorobyev.atostest.businesslogic.repository.AuthorizationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApiHelper(): ApiHelper = ApiHelper(provideAuthorizationDb().authorizationDao())

    @Provides
    @Singleton
    fun provideAuthorizationDb(): AuthorizationDb =
        AuthorizationDb.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun provideRoomDb(): RoomDB = RoomDB.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun provideRoomRepository(): RoomRepository = RoomRepository(provideApiHelper(),
        provideRoomDb().roomDao())

    @Provides
    @Singleton
    fun provideAuthorizationRepository(): AuthorizationRepository =
        AuthorizationRepository(provideApiHelper(),
            provideAuthorizationDb().authorizationDao())

    @Provides
    @Singleton
    fun provideReservationRepository(): ReservationRepository =
        ReservationRepository(provideApiHelper())

}