package com.whiteside.cafe.Shop

import Model.Offer

interface OnGetOffersListener {
    open fun onGetOfferSuccess(offer: Offer?)
    open fun onGetOfferFailed(e: Exception?)
}