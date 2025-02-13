package com.example.itemgrouping.item_names

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.itemgrouping.ui.theme.ItemGroupingTheme

//TODO add snapshot test with Paparazzi
@Composable
fun ItemNamesRoute(navController: NavController, modifier: Modifier = Modifier) {
    ItemNames(navController = navController, modifier = modifier)
}

@Composable
private fun ItemNames(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: ItemNamesViewmodel = hiltViewModel<ItemNamesViewmodel>(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    ItemNamesContent(
        navController = navController,
        state = state,
        modifier = modifier
    )
}

@Composable
private fun ItemNamesContent(
    navController: NavController,
    state: ItemNamesViewState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(state.groupedItemsViewType) {
            when (it) {
                is ItemNamesViewType.Header -> it.Content()

                is ItemNamesViewType.Item -> it.Content()
            }
        }
                    // todo navigate to detail view with navController
    }
}

@Preview(showBackground = true)
@Composable
internal fun FeedPreview() = ItemGroupingTheme {
    ItemNames(navController = rememberNavController())
}
