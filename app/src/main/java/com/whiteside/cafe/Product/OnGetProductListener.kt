package com.whiteside.cafe.Product

import Model.Product

interface OnGetProductListener {
    open fun onGetProductSuccess(product: Product?)
    open fun onGetProductFailed(e: Exception?)
}