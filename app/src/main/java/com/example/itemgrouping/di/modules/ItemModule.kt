package com.example.itemgrouping.di.modules

import com.example.itemgrouping.data.repository.ItemRepositoryImpl
import com.example.itemgrouping.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ItemRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository
}