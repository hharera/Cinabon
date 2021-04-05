package com.whiteside.cafe.ui.offer

import com.whiteside.cafe.model.Offer

interface OnGetOfferListener {
    open fun onSuccess(offer: Offer)
    open fun onFailed(e: Exception)
}