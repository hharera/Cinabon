package com.whiteside.cafe.ui.product

import com.whiteside.cafe.model.Product

interface OnGetProductListener {
    open fun onGetProductSuccess(product: Product?)
    open fun onGetProductFailed(e: Exception?)
}