package com.pavelvorobyev.atostest.businesslogic.api

import com.pavelvorobyev.atostest.businesslogic.db.authorization.AuthorizationDao
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val authorizationDao: AuthorizationDao) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = try {
            authorizationDao.getAuthorizations()[0].token
        }catch (e: Exception) {
            e.printStackTrace()
            ""
        }

        return chain.proceed(chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build())
    }
}