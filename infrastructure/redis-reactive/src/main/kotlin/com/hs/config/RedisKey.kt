package com.hs.config

object RedisKey {
    fun getProductKey(id: Long): String = "products:$id"
}
