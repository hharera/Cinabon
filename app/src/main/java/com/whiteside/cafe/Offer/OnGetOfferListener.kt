package com.whiteside.cafe.Offer;

import Model.Offer;

public interface OnGetOfferListener {

    void onSuccess(Offer offer);

    void onFailed(Exception e);
}
