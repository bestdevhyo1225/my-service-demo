package com.hs.handler

import com.hs.exception.RequestFailException
import com.hs.response.ErrorResponse
import okhttp3.ResponseBody
import org.springframework.stereotype.Component
import retrofit2.Retrofit

@Component
class RetrofitErrorResponseHandler(
    private val commandApiRetrofit: Retrofit,
) {

    fun throwCommandApiError(errorBody: ResponseBody) {
        val errorResponse = commandApiRetrofit.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            ErrorResponse::class.java.annotations
        ).convert(errorBody)!!

        throw RequestFailException("${errorResponse.status} ${errorResponse.message}")
    }
}
