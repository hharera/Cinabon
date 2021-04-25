package com.whiteside.cafe.ui.search

import com.whiteside.cafe.common.BaseListener

interface SearchListener<T> : BaseListener<T> {

    fun onEmptySearch()
}