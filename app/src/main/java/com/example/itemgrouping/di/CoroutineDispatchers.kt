package com.example.itemgrouping.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchers internal constructor(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val network: CoroutineDispatcher
) {
    companion object {
        fun default() = CoroutineDispatchers(
            main = Dispatchers.Main,
            computation = Dispatchers.Default,
            io = Dispatchers.IO,
            network = Dispatchers.IO
        )
    }
}