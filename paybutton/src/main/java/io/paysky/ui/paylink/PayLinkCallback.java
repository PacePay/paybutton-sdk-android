package io.paysky.ui.paylink;

import io.paysky.data.model.request.InitiateOrderRequest;
import io.paysky.data.model.response.InitiateOrderResponse;

public interface PayLinkCallback {
    void onSuccess(InitiateOrderRequest request, InitiateOrderResponse response);
    void onError(String message);
}
