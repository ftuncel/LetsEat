package com.ferhattuncel.letseat.di

import com.ferhattuncel.letseat.data.datasource.ProductDataSource
import com.ferhattuncel.letseat.data.repo.ProductRepository
import com.ferhattuncel.letseat.retrofit.ApiUtils
import com.ferhattuncel.letseat.retrofit.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesProductRepository(productDataSource: ProductDataSource) : ProductRepository{
        return ProductRepository(productDataSource)

    }
    @Provides
    @Singleton
    fun providesProductDataSource(productDao: ProductDao) : ProductDataSource{
        return ProductDataSource(productDao)
    }
    @Provides
    @Singleton
    fun providesProductDao() : ProductDao{
        return ApiUtils.getProductDao()
    }

}