package com.example.itemgrouping.di.modules

import com.example.itemgrouping.data.service.ItemService
import com.example.itemgrouping.di.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    @Singleton
    @Provides
    fun bindRetrofitClient(): ItemService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ItemService::class.java)

    @Singleton
    @Provides
    fun coroutineDispatchers() = CoroutineDispatchers.default()
}