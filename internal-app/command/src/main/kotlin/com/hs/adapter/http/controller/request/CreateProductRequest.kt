package com.hs.adapter.http.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero

data class CreateProductRequest(

    @field:NotBlank(message = "name 필드는 비어 있을 수 없습니다.")
    var name: String,

    @field:PositiveOrZero(message = "price 필드는 0보다 같거나 큰 정수 값이어야 합니다.")
    var price: Int,

    @field:PositiveOrZero(message = "stockQuantity 필드는 0보다 같거나 큰 정수 값이어야 합니다.")
    var stockQuantity: Int
)
