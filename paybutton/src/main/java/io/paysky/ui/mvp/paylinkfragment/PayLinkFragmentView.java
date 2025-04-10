package io.paysky.ui.mvp.paylinkfragment;

import io.paysky.data.model.request.InitiateOrderRequest;
import io.paysky.data.model.response.InitiateOrderResponse;
import io.paysky.ui.mvp.BaseView;


public interface PayLinkFragmentView extends BaseView {
    void showPayLinkDialog(InitiateOrderRequest initiateOrder, InitiateOrderResponse body);

    void hideDataForm();
}
