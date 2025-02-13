package com.example.itemgrouping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.itemgrouping.item_names.ItemNamesRoute
import com.example.itemgrouping.ui.theme.ItemGroupingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ItemGroupingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ItemNamesRoute(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object ItemNames : DestinationScreen("itemNames")
    object Detail : DestinationScreen("detail")
    // to do add detail view for master detail layout ItemName(val itemId: Int) : DestinationScreen("itemNames/{itemId}") for specific item name
}

@Composable
fun InstagramApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.ItemNames.route
    ) {
        composable(DestinationScreen.ItemNames.route) {
            ItemNamesRoute(navController = navController, modifier = modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ItemGroupingTheme {
        InstagramApp()
    }
}