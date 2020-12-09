package com.example.elsafa.ui.shop;

import Model.Offer.Offer;

public interface OnGetOffersListener {

    void onSuccess(Offer offer);

    void onFailed(Exception e);
}
