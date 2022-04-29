package com.hs.handler

import com.hs.accessor.CommandApiAccessor
import com.hs.config.Resilience4jConfig.Api
import com.hs.dto.service.output.FindProductResultDto
import com.hs.exception.RequestFailException
import com.hs.response.SuccessResponse
import com.hs.service.ProductSyncHandler
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

@Component
class RetrofitProductSyncHandler(
    private val commandAppAccessor: CommandApiAccessor,
    private val retrofitErrorResponseHandler: RetrofitErrorResponseHandler,
) : ProductSyncHandler {

    @CircuitBreaker(name = Api.COMMAND)
    override fun execute(productId: Long): Any {
        val call: Call<SuccessResponse<FindProductResultDto>> = commandAppAccessor.getProduct(productId = productId)

        val requestFunction = {
            val response: Response<SuccessResponse<FindProductResultDto>> = call.execute()

            if (!response.isSuccessful) {
                retrofitErrorResponseHandler.throwCommandApiError(errorBody = response.errorBody()!!)
            }

            response.body()!!.data
        }

        return this.get(apiName = Api.COMMAND, requestFunction = requestFunction)
    }

    fun <T> get(apiName: String, requestFunction: () -> T): T {
        return try {
            requestFunction()
        } catch (exception: IOException) {
            throw RuntimeException("$apiName - I/O Error (" + exception.localizedMessage + ")")
        } catch (exception: RequestFailException) {
            throw RuntimeException("$apiName - Response Error (" + exception.localizedMessage + ")")
        }
    }
}
