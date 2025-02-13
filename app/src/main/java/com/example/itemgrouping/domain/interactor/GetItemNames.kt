package com.example.itemgrouping.domain.interactor

import com.example.itemgrouping.domain.repository.ItemRepository
import com.example.itemgrouping.models.ItemName
import javax.inject.Inject

class GetItemNames @Inject constructor(private val itemRepository: ItemRepository) {

    suspend operator fun invoke(): List<ItemName>? {
        return itemRepository.getItemNames()
    }
}