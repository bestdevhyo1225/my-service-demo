package com.hs.adapter.http.controller

import com.hs.application.service.ProductFindReactiveQuery
import com.hs.dto.service.output.FindProductMetaDto
import com.hs.response.SuccessResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/query/products")
class ProductReactiveController(
    private val productFindReactiveQuery: ProductFindReactiveQuery
) {

    @GetMapping("/{id}")
    fun findProduct(@PathVariable id: Long): Mono<SuccessResponse<FindProductMetaDto>> {
        return productFindReactiveQuery
            .execute(id = id)
            .flatMap { Mono.just(SuccessResponse(data = it)) }
    }
}
