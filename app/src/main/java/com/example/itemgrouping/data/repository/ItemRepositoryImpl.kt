package com.example.itemgrouping.data.repository

import android.util.Log
import com.example.itemgrouping.data.service.ItemService
import com.example.itemgrouping.di.CoroutineDispatchers
import com.example.itemgrouping.domain.repository.ItemRepository
import com.example.itemgrouping.models.ItemName
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemService: ItemService,
    private val dispatchers: CoroutineDispatchers,
) : ItemRepository {
    override suspend fun getItemNames(): List<ItemName>? {
        return withContext(dispatchers.network) {
            try {
                val response = itemService.getItemNames()
                Log.e("::Logged ItemRepositoryImpl", "Fetching items successful")
                Result.success(response.body())
            } catch (e: Exception) {
                Log.e("::Logged ItemRepositoryImpl", "Error fetching item names", e)
                Result.failure(e)
            }
        }.getOrNull()
    }
}
