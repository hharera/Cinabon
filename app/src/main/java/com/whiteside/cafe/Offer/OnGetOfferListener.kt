package com.whiteside.cafe.Offer

import Model.Offer

interface OnGetOfferListener {
    open fun onSuccess(offer: Offer?)
    open fun onFailed(e: Exception?)
}