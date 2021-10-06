package com.harera.managehyper.ui.add_category

data class CategoryFormState(
    val categoryNameError:  Int? = null,
    val categoryImageError:  Int? = null,
    val isValid: Boolean = false,
)
