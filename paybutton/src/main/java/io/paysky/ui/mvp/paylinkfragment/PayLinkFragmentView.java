package io.paysky.upg.mvp.paylinkfragment;

import io.paysky.upg.base.BaseView;
import io.paysky.upg.data.network.model.request.InitiateOrderRequest;
import io.paysky.upg.data.network.model.response.InitiateOrderResponse;

public interface PayLinkFragmentView extends BaseView {
    void showPayLinkDialog(InitiateOrderRequest initiateOrder, InitiateOrderResponse body);

    void hideDataForm();
}
