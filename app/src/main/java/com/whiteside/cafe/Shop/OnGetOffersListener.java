package com.whiteside.cafe.Shop;

import Model.Offer;

public interface OnGetOffersListener {

    void onGetOfferSuccess(Offer offer);

    void onGetOfferFailed(Exception e);
}
