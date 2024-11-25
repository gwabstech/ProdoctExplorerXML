package com.gwabs.prodoctexplorerxml.data.api

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.gwabs.prodoctexplorerxml.data.database.AppDatabase
import com.gwabs.prodoctexplorerxml.data.database.ProductDao
import com.gwabs.prodoctexplorerxml.data.repository.ProductRepository
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/") // Change this to your API base URL
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }


    // Provide the application context
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "product_database"
        ).build()
    }

    // Provide ProductDao
    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()  // Provides the ProductDao from Room Database
    }

    // Provide ProductRepository
    @Provides
    @Singleton
    fun provideProductRepository(
        productApi: ProductApi,
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepository(productApi, productDao)
    }
}
