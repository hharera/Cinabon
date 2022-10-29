package com.harera.insertproduct

data class ProductFormState(
    val categoryNameError: Int? = null,
    val categoryImageError: Int? = null,
    val isValid: Boolean = false,
)
