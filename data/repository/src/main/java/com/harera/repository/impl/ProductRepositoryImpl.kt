package com.harera.repository.impl

import android.graphics.Bitmap
import com.harera.hyperpanda.local.MarketDao
import com.harera.repository.abstraction.ProductRepository
import com.harera.repository.domain.Product
import com.harera.repository.mapper.ProductMapper
import com.harera.repository.uitls.Resource
import com.harera.repository.uitls.networkBoundResource
import com.harera.service.abstraction.ProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productDao: MarketDao,
) : ProductRepository {

    override fun insertProduct(product: Product, forceOnline: Boolean): Flow<Boolean> = flow {
        productService.addProduct(ProductMapper.toProduct(product))
    }

    override fun getProduct(productId: String, forceOnline: Boolean): Flow<Resource<Product>> =
        networkBoundResource(
            query = {
                ProductMapper.toProduct(productDao.getProduct(productId))
            },
            fetch = {
                ProductMapper.toProduct(productService.getProduct(productId)!!)
            },
            saveFetchResult = { product ->
                productDao.insertProduct(ProductMapper.toProductEntity(product))
            },
            shouldFetch = {
                forceOnline
            }
        )

    override fun uploadProductImage(productId: String, imageBitmap: Bitmap): Flow<Boolean> = flow {
        productService.uploadProductImage(productId, imageBitmap)
    }

    override fun uploadProductImage(
        productId: String,
        productImageUrl: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getProducts(limit: Int): Flow<List<Product>> = flow {
        productService.getProducts(limit)
    }

    override fun updateProductImage(productId: String, productImageUrl: String): Flow<Boolean> =
        flow {
            productService.updateProductImage(productId, productImageUrl)
        }
}