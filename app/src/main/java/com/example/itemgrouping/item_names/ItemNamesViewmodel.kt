package com.example.itemgrouping.item_names

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemgrouping.domain.interactor.GetItemNames
import com.example.itemgrouping.models.ItemName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemNamesViewmodel @Inject constructor(private val getItemNames: GetItemNames) :
    ViewModel() {
    private val defaultState = ItemNamesViewState.Empty
    private val searchIdState = MutableStateFlow(defaultState.searchID)
    private val itemsViewTypeState = MutableStateFlow(defaultState.groupedItemsViewType)
    private val isLoadingState = MutableStateFlow(defaultState.isLoading)
    private val errorState = MutableStateFlow(defaultState.error)
    val state = combine(
        searchIdState,
        itemsViewTypeState,
        isLoadingState,
        errorState,
        ::ItemNamesViewState
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), // state resets after 5 seconds when no subscribers are present
        defaultState
    )

    init {
        getSortedGroupedItems()
    }

    @VisibleForTesting
    fun getSortedGroupedItems() = viewModelScope.launch {
        isLoadingState.value = true
        runCatching { getItemNames() }
            .onSuccess {
                when {
                    it.isNullOrEmpty() -> errorState.value = Exception("Empty or null ItemNames")
                    else -> itemsViewTypeState.value = getSortedItemsByItemViewType(it)
                }
            }.onFailure {
                errorState.value = it
            }
        isLoadingState.value = false
    }

    @VisibleForTesting
    fun getSortedItemsByItemViewType(names: List<ItemName>): List<ItemNamesViewType> {
        val map = names.groupBy { it.listId }
        val sortedValues = mutableListOf<ItemNamesViewType>()
        val sortedListIds = map.keys.sorted() // sort the names by list id
        for (key in sortedListIds) {
            val values = map[key] ?: continue
            sortedValues += ItemNamesViewType.Header(values[0].listId)
            sortedValues += parseAndSortNames(values).map { ItemNamesViewType.Item(it) }
        }
        return sortedValues
    }

    /**
     * sorts the names in ascending order by the number in the name
     */
    private fun parseAndSortNames(values: List<ItemName>): List<ItemName> = try {
        values.sortedBy {
            it.name?.substringAfter("Item ")?.toInt()
        }
    } catch (e: Exception) {
        Log.e("::Logged ItemNamesViewmodel", "Error sorting names", e)
        values.sortedBy { it.name }
    }

    fun updateAndFilter(searchString: String) {
        searchIdState.value = searchString
        filterSearchId(searchString)
    }

    private fun filterSearchId(enteredListId: String) = viewModelScope.launch {
        getItemNames()?.let {
            try {
                val filtered = it.filter { it.listId == enteredListId.toInt() }
                when {
                    filtered.isEmpty() -> resetIdList()
                    else -> itemsViewTypeState.value = getSortedItemsByItemViewType(filtered)
                }
            } catch (e: Exception) {
                errorState.value = Exception("Error filtering item names")
                Log.e("::logged ItemNamesViewmodel", "Error filtering item names $e")
            }
        }
    }

    private fun resetIdList() {
        getSortedGroupedItems()
    }
}