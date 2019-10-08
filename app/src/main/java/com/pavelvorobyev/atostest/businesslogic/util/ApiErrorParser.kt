package com.pavelvorobyev.atostest.businesslogic.util

import com.pavelvorobyev.atostest.businesslogic.api.ApiError
import org.json.JSONObject

object ApiErrorParser {

    fun parse(json: String, code: Int): ApiError {
        val errorJson = JSONObject(json)

        return when  (errorJson.has("error")) {
            true -> {
                val errorObjJson = errorJson.getJSONObject("error")
                val pointer = errorObjJson.getString("pointer")
                val reason = errorObjJson.getString("reason")

                ApiError(pointer, reason, code)
            }
            false -> ApiError("", "", code)
        }
    }

}