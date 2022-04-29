package com.hs.adapter.http.controller

import com.hs.adapter.http.controller.request.CreateProductRequest
import com.hs.adapter.http.controller.request.UpdateProductRequest
import com.hs.application.service.command.ProductCreateCommand
import com.hs.application.service.command.ProductUpdateCommand
import com.hs.application.service.query.ProductEventFindQuery
import com.hs.application.service.query.ProductFindQuery
import com.hs.dto.FindAllPageableDto
import com.hs.dto.service.input.CreateProductDto
import com.hs.dto.service.input.UpdateProductDto
import com.hs.dto.service.output.CreateProductResultDto
import com.hs.dto.service.output.FindProductEventResultDto
import com.hs.dto.service.output.FindProductResultDto
import com.hs.response.SuccessResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductController(
    private val productCreateCommand: ProductCreateCommand,
    private val productUpdateCommand: ProductUpdateCommand,
    private val productFindQuery: ProductFindQuery,
    private val productEventFindQuery: ProductEventFindQuery
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveProduct(
        @Valid @RequestBody
        request: CreateProductRequest
    ): ResponseEntity<SuccessResponse<CreateProductResultDto>> {
        val createProductResultDto = productCreateCommand.execute(
            serviceDto = CreateProductDto(
                name = request.name,
                price = request.price,
                stockQuantity = request.stockQuantity
            )
        )

        return ResponseEntity.ok(SuccessResponse(data = createProductResultDto))
    }

    @PatchMapping("/{id}")
    fun updateProduct(
        @PathVariable
        id: Long,
        @Valid @RequestBody
        request: UpdateProductRequest
    ): ResponseEntity<SuccessResponse<Any>> {
        productUpdateCommand.execute(
            serviceDto = UpdateProductDto(
                productId = id,
                name = request.name,
                price = request.price,
                stockQuantity = request.stockQuantity
            )
        )

        return ResponseEntity.ok(SuccessResponse(data = object {}))
    }

    @GetMapping("/{id}")
    fun findProduct(
        @PathVariable
        id: Long
    ): ResponseEntity<SuccessResponse<FindProductResultDto>> {
        val findProductResultDto = productFindQuery.execute(productId = id)

        return ResponseEntity.ok(SuccessResponse(data = findProductResultDto))
    }

    @GetMapping("/{id}/events")
    fun findProductEvents(
        @PathVariable
        id: Long,
        @RequestParam(defaultValue = "0")
        start: Long,
        @RequestParam(defaultValue = "10")
        count: Long,
    ): ResponseEntity<SuccessResponse<FindAllPageableDto<FindProductEventResultDto>>> {
        val findAllPageableDto = productEventFindQuery.execute(productId = id, start = start, count = count)

        return ResponseEntity.ok(SuccessResponse(data = findAllPageableDto))
    }
}
