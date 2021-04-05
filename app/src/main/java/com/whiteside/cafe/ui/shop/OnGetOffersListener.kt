package com.whiteside.cafe.ui.shop

import com.whiteside.cafe.model.Offer

interface OnGetOffersListener {
    open fun onGetOfferSuccess(offer: Offer)
    open fun onGetOfferFailed(e: Exception)
}