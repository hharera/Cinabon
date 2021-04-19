package com.whiteside.cafe.common.repository

import com.whiteside.cafe.model.Item

interface WishListRepository {

    fun addItem(item: Item)
    fun removeItem(item: Item)
}