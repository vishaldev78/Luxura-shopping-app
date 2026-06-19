package com.shops.ecomm.di

import com.shops.ecomm.data.repository.CategoryRepositoryImpl
import com.shops.ecomm.data.repository.LocationRepositoryImpl
import com.shops.ecomm.data.repository.ProductRepositoryImpl
import com.shops.ecomm.domain.repository.CategoryRepository
import com.shops.ecomm.domain.repository.LocationRepository
import com.shops.ecomm.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository


    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository



    @Binds
    abstract fun bindLocationRepository(
        impl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    abstract fun bindCartRepository(
        impl: com.shops.ecomm.data.repository.CartRepositoryImpl
    ): com.shops.ecomm.domain.repository.CartRepository

    @Binds
    abstract fun bindOrderRepository(
        impl: com.shops.ecomm.data.repository.OrderRepositoryImpl
    ): com.shops.ecomm.domain.repository.OrderRepository
}
