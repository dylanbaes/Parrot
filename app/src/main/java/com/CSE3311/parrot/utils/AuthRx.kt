package com.CSE3311.parrot.utils

import com.parse.ParseCloud
import io.reactivex.Single
/*
 * code used based on demo code provided by virgil security under apache 2.0 license
 * AuthRx
 */

object AuthRx {

    /**
     * You can call it only after successful [authenticate]
     */
    fun virgilJwt(sessionToken: String) = Single.create<String> { emitter ->
        val requestParams = mutableMapOf<String, String>().apply {
            put("sessionToken", sessionToken)
        }

        ParseCloud.callFunctionInBackground<Map<String, Any>>(
                KEY_VIRGIL_JWT,
                requestParams
        ) { virgilJwt, exception ->
            if (exception == null)
                emitter.onSuccess(virgilJwt[KEY_TOKEN].toString())
            else
                emitter.onError(exception)

        }
    }

    private const val KEY_VIRGIL_JWT = "virgil-jwt"
    private const val KEY_TOKEN = "token"
}