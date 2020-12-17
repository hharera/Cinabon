package com.example.elsafa.Shop;

import Model.Offer;

public interface OnGetOffersListener {

    void onGetOfferSuccess(Offer offer);

    void onGetOfferFailed(Exception e);
}
