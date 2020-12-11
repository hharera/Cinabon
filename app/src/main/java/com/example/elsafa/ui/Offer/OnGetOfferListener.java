package com.example.elsafa.ui.Offer;

import Model.Offer.Offer;

public interface OnGetOfferListener {

    void onSuccess(Offer offer);

    void onFailed(Exception e);
}
