package com.pavelvorobyev.atostest.businesslogic.repository

import com.pavelvorobyev.atostest.businesslogic.api.ApiHelper
import com.pavelvorobyev.atostest.businesslogic.api.ApiResponse
import com.pavelvorobyev.atostest.businesslogic.pojo.Authorization
import com.pavelvorobyev.atostest.businesslogic.pojo.SignInBody
import com.pavelvorobyev.atostest.businesslogic.db.authorization.AuthorizationDao
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlin.concurrent.thread

class AuthorizationRepository(private val apiHelper: ApiHelper,
                              private val authorizationDao: AuthorizationDao
) {

    fun signIn(body: SignInBody): Observable<ApiResponse<Authorization>> {
        return apiHelper.userService.signIn(body)
    }

    fun getAuthorizationsFlowable(): Flowable<List<Authorization>> {
        return authorizationDao.getAuthorizationsFlowable()
    }

    fun insetAuthorization(authorization: Authorization) {
        thread {
            authorizationDao.insert(authorization)
        }
    }

}