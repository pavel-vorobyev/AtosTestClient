package com.pavelvorobyev.atostest.businesslogic.repository

import com.pavelvorobyev.atostest.businesslogic.api.ApiError

class RepositoryResponse<D>(
    var data: D?,
    var error: ApiError?,
    var status: Status?
) {

    companion object {

        fun <D>loading(): RepositoryResponse<D> {
            return RepositoryResponse(null, null, Status.LOADING)
        }

        fun <D>success(data: D?): RepositoryResponse<D> {
            return RepositoryResponse(data, null, Status.SUCCESS)
        }

        fun <D>error(error: ApiError?): RepositoryResponse<D> {
            return RepositoryResponse(null, error, Status.ERROR)
        }

    }

}

enum class Status {

    LOADING,
    SUCCESS,
    ERROR

}