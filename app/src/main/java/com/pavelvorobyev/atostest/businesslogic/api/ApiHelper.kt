package com.pavelvorobyev.atostest.businesslogic.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.pavelvorobyev.atostest.BuildConfig
import com.pavelvorobyev.atostest.businesslogic.db.authorization.AuthorizationDao
import com.pavelvorobyev.atostest.businesslogic.repository.AuthorizationRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiHelper
@Inject
constructor(private val authorizationDao: AuthorizationDao) {

    private fun getHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(AuthorizationInterceptor(authorizationDao))
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val userService: ApiServices.UserService = retrofit.create(ApiServices.UserService::class.java)
    val roomService: ApiServices.RoomService = retrofit.create(ApiServices.RoomService::class.java)
    val reservationService: ApiServices.ReservationService =
        retrofit.create(ApiServices.ReservationService::class.java)

}