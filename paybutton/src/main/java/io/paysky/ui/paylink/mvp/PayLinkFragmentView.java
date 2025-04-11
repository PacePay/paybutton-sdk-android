package io.paysky.ui.paylink.mvp;



import io.paysky.data.model.request.InitiateOrderRequest;
import io.paysky.data.model.response.InitiateOrderResponse;


public interface PayLinkFragmentView {
    void showPayLinkDialog(InitiateOrderRequest initiateOrder, InitiateOrderResponse body);

    void showError(String message);

}
