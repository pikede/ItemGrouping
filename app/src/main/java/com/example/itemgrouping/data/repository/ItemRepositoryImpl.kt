package com.example.itemgrouping.data.repository

import android.util.Log
import com.example.itemgrouping.data.service.ItemService
import com.example.itemgrouping.di.CoroutineDispatchers
import com.example.itemgrouping.domain.repository.ItemRepository
import com.example.itemgrouping.models.ItemName
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemService: ItemService,
    private val dispatchers: CoroutineDispatchers,
) : ItemRepository {
    override suspend fun getItemNames(): List<ItemName>? {
        return runCatching { itemService.getItemNames() }.onSuccess {
            Log.d("::Logged ItemRepositoryImpl", "Fetching items successful")
        }.onFailure {
            Log.e("::Logged ItemRepositoryImpl", "Error fetching item names", it)
        }.getOrThrow()
    }
}
