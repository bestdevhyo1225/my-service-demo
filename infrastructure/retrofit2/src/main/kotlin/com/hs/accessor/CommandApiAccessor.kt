package com.hs.accessor

import com.hs.dto.service.output.FindProductResultDto
import com.hs.response.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommandApiAccessor {

    @GET("/products/{id}")
    fun getProduct(@Path("id") productId: Long): Call<SuccessResponse<FindProductResultDto>>
}
