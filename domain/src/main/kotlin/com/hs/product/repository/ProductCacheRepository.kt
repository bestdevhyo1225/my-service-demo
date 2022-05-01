package com.hs.product.repository

import java.util.concurrent.TimeUnit

interface ProductCacheRepository {
    fun <T : Any> set(key: String, value: T, expireTime: Long, timeUnit: TimeUnit)
    fun <T> get(key: String, clazz: Class<T>): T?
    fun <T> getList(keys: List<String>): List<T?>
}
