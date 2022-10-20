package com.harera.repository.abstraction.di

import com.harera.repository.abstraction.*
import com.harera.repository.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindProductRepository(productRepository: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindCartRepository(cartRepository: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindWishListRepository(wishListRepository: WishRepositoryImpl): WishListRepository

}