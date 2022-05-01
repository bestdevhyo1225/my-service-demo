package com.hs.adapter.http.controller

import com.hs.application.service.ProductFindQuery
import com.hs.dto.service.output.FindProductMetaDto
import com.hs.response.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query/products")
class ProductController(
    private val productFindQuery: ProductFindQuery
) {

    @GetMapping("/{id}")
    fun findProduct(
        @PathVariable
        id: Long
    ) : ResponseEntity<SuccessResponse<FindProductMetaDto>> {
        val productMetadataDto = productFindQuery.execute(id = id)

        return ResponseEntity.ok(SuccessResponse(data = productMetadataDto))
    }
}
