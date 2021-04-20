package com.whiteside.cafe.ui.search

import com.whiteside.cafe.api.firebase.FirebaseProductRepository
import javax.inject.Inject

class SearchResultsPresenter @Inject constructor(val repo: FirebaseProductRepository)