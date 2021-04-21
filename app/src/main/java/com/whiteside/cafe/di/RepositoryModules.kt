package com.whiteside.cafe.di

import com.whiteside.cafe.api.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Binds
    abstract fun bindAuthManager(): AuthManager

    @Binds
    abstract fun bindCartRepository(): CartRepository

    @Binds
    abstract fun bindCategoryRepository(): CategoryRepository

    @Binds
    abstract fun bindOfferRepository(): OfferRepository

    @Binds
    abstract fun bindWishListRepository(): WishListRepository

    @Binds
    abstract fun bindUserRepository(): UserRepository

    @Binds
    abstract fun bindProductRepository(): ProductRepository

}