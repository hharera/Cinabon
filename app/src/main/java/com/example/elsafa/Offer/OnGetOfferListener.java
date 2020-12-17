package com.example.elsafa.Offer;

import Model.Offer;

public interface OnGetOfferListener {

    void onSuccess(Offer offer);

    void onFailed(Exception e);
}
