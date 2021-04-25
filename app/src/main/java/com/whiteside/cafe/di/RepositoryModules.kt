package com.whiteside.cafe.di

import com.whiteside.cafe.api.firebase.*
import com.whiteside.cafe.api.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModules {

    @Binds
    abstract fun bindAuthManager(manager: FirebaseAuthManager): AuthManager

    @Binds
    abstract fun bindCartRepository(repository: FirebaseCartRepository): CartRepository

    @Binds
    abstract fun bindCategoryRepository(repository: FirebaseCategoryRepository): CategoryRepository

    @Binds
    abstract fun bindOfferRepository(repository: FirebaseOfferRepository): OfferRepository

    @Binds
    abstract fun bindWishListRepository(repository: FirebaseWishListRepository): WishListRepository

    @Binds
    abstract fun bindUserRepository(repository: FirebaseUserRepo): UserRepository

    @Binds
    abstract fun bindProductRepository(repository: FirebaseProductRepository): ProductRepository

    @Binds
    abstract fun bindSearchRepository(repository: FirebaseSearchRepository): SearchRepository

}