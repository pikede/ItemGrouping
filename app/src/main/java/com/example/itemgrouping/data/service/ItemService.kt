package com.example.itemgrouping.data.service

import com.example.itemgrouping.models.ItemName
import retrofit2.Response
import retrofit2.http.GET

interface ItemService {
    @GET("hiring.json")
    suspend fun getItemNames(): List<ItemName>
}