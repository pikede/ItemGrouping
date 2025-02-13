package com.example.itemgrouping.domain.repository

import com.example.itemgrouping.models.ItemName

interface ItemRepository {
    suspend fun getItemNames(): List<ItemName>?
}