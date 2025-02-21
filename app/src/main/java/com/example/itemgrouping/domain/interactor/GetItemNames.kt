package com.example.itemgrouping.domain.interactor

import com.example.itemgrouping.domain.repository.ItemRepository
import com.example.itemgrouping.models.ItemName
import javax.inject.Inject

class GetItemNames @Inject constructor(private val itemRepository: ItemRepository) {

    suspend operator fun invoke(): List<ItemName>? {
        val itemNames = itemRepository.getItemNames()
        return filterInvalidNames(itemNames.orEmpty())
    }

    /**
     * filters out empty and null names
     */
    private fun filterInvalidNames(names: List<ItemName>): List<ItemName> {
        return names.filter { it.name != null && it.name.isNotEmpty() == true }
    }
}
