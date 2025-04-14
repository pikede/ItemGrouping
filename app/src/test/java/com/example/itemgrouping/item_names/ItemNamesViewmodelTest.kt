package com.example.itemgrouping.item_names

import android.util.Log
import app.cash.turbine.test
import com.example.itemgrouping.domain.interactor.GetItemNames
import com.example.itemgrouping.models.ItemName
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemNamesViewmodelTest {

    lateinit var viewModel: ItemNamesViewmodel

    @MockK
    lateinit var getItemNames: GetItemNames

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic(Log::class)
        every { Log.e(any(),  any(), any()) } returns   0
        every { Log.d(any(),  any()   ) } returns   0
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ItemNamesViewmodel(getItemNames)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getSortedGroupedItems failure`() = runTest {
        val expectedError = Exception("Error fetching item names")
        coEvery { getItemNames.invoke() } throws expectedError

        viewModel.getSortedGroupedItems()

        viewModel.state.test {
            val item = awaitItem()
            assertEquals(expectedError, item.error)
        }
    }

    @Test
    fun `test getSortedGroupedItems success`() = runTest {
        val successResponse = getSuccessResponse()
        val sortedByViewType = viewModel.getSortedItemsByItemViewType(successResponse)
        coEvery { getItemNames.invoke() } returns successResponse

        viewModel.getSortedGroupedItems()

        viewModel.state.test {
            val item = awaitItem()
            assertEquals(sortedByViewType, item.groupedItemsViewType)
        }
    }

    fun getSuccessResponse() = listOf(
        ItemName(
            id = 0,
            listId = 0,
            name = "ItemName 0",
        ),
        ItemName(
            id = 1,
            listId = 3,
            name = "ItemName 3",
        ),
        ItemName(
            id = 0,
            listId = 2,
            name = "ItemName 2",
        ),
        ItemName(
            id = 0,
            listId = 1,
            name = "ItemName 3",
        ),
        ItemName(
            id = 0,
            listId = 1,
            name = "ItemName 4",
        )
    )
}