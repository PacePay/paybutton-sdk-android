package io.paysky.ui.paylink.mvp;


import io.paysky.ui.paylink.PayLinkCallback;
import io.paysky.ui.paylink.base.BasePayLinkPresenter;

public class PayLinkFragmentPresenter extends BasePayLinkPresenter<PayLinkFragmentView> {

    private final PayLinkFragmentManager manager = new PayLinkFragmentManager();

    public void setCallback(PayLinkCallback callback) {
        manager.setCallback(callback);
    }

    @Override
    public void attachView(PayLinkFragmentView view) {
        super.attachView(view);
    }

    public void initiateOrder(String selectedTerminal, String merchantId, String merchantSecureHash,
                              String selectedCurrency, String currencyName, String dateExpire, String payerName,
                              String notificationMethod, String notification, String referenceNumber, String amount,
                              String amountTransaction, String numberOfPayment, String image, String message) {
        manager.initiateOrder(selectedTerminal, merchantId, merchantSecureHash, selectedCurrency, currencyName,
                dateExpire, payerName, notificationMethod, notification, referenceNumber,
                amount, amountTransaction, numberOfPayment, image, message);
    }

    @Override
    public void detachView() {
        manager.setCallback(null);
        super.detachView();
    }
}
