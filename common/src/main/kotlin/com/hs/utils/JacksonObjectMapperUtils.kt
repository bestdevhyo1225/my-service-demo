package com.hs.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JacksonObjectMapperUtils {
    fun <T> convertObject(value: String, clazz: Class<T>): T = jacksonObjectMapper().readValue(value, clazz)

    fun <T : Any> convertString(data: T): String = jacksonObjectMapper().writeValueAsString(data)
}
