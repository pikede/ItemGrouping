package com.example.itemgrouping.item_names

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.itemgrouping.models.ItemName
import com.example.itemgrouping.R

data class ItemNamesViewState(
    val searchID: String = "",
    val groupedItemsViewType: List<ItemNamesViewType> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
) {
    companion object {
        val Empty = ItemNamesViewState()
    }
}

sealed interface ItemNamesViewType {
    data class Header(val listId: Int) : ItemNamesViewType {
        @Composable
        fun Content(modifier: Modifier = Modifier) = Text(
            text = stringResource(R.string.group_header, listId),
            modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            color = Color.Black
        )
    }

    data class Item(val itemName: ItemName) : ItemNamesViewType {
        @Composable
        fun Content(modifier: Modifier = Modifier) = Text(
            text = "${itemName.listId} - ${itemName.name.orEmpty()}",
            modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}