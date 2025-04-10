package io.paysky.ui.mvp.paylinkfragment;


import io.paysky.ui.mvp.BasePresenter;

public class PayLinkFragmentPresenter extends BasePresenter<PayLinkFragmentView> {

    PayLinkFragmentManager manager = new PayLinkFragmentManager();


    @Override
    public void attachView(PayLinkFragmentView view) {
        super.attachView(view);
        manager.setView(view);
    }


    public void initiateOrder(String selectedTerminal,String merchantId,String merchantSecureHash, String selectedCurrancy, String currencyName
            , String dateExpire, String payerName, String notificationMethod, String notification, String referenceNumber,
                              String amount, String amountTransaction, String numberOfPayment, String Image, String message) {
        manager.initiateOrder(selectedTerminal,merchantId,merchantSecureHash, selectedCurrancy, currencyName, dateExpire, payerName,
                notificationMethod, notification
                , referenceNumber, amount, amountTransaction, numberOfPayment, Image, message);
    }


    public void hideData() {
        manager.hideDataForm();
    }

    @Override
    public void detachView() {
        manager.setView(null);
        super.detachView();
    }
}
