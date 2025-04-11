package io.paysky.ui.paylink;

import android.content.Context;

import io.paysky.ui.paylink.mvp.PayLinkFragmentPresenter;
import io.paysky.ui.paylink.mvp.PayLinkFragmentView;

public class PayLinkSdkBuilder {
    private PayLinkFragmentView payLinkFragmentView;  // The custom view
    private PayLinkCallback callback;

    private String selectedTerminal;
    private String merchantId;
    private String merchantSecureHash;
    private String selectedCurrency;
    private String currencyName;
    private String dateExpire;
    private String payerName;
    private String notificationMethod;
    private String notification;
    private String referenceNumber;
    private String amount;
    private String amountTransaction;
    private String numberOfPayment;
    private String image;
    private String message;

    public PayLinkSdkBuilder() {

    }
    public PayLinkSdkBuilder setCallback(PayLinkCallback callback) {
        this.callback = callback;
        return this;
    }
    public PayLinkSdkBuilder( PayLinkFragmentView payLinkFragmentView) {

        this.payLinkFragmentView = payLinkFragmentView;
    }

    public PayLinkSdkBuilder setSelectedTerminal(String selectedTerminal) {
        this.selectedTerminal = selectedTerminal;
        return this;
    }

    public PayLinkSdkBuilder setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public PayLinkSdkBuilder setMerchantSecureHash(String merchantSecureHash) {
        this.merchantSecureHash = merchantSecureHash;
        return this;
    }

    public PayLinkSdkBuilder setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
        return this;
    }

    public PayLinkSdkBuilder setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
        return this;
    }

    public PayLinkSdkBuilder setDateExpire(String dateExpire) {
        this.dateExpire = dateExpire;
        return this;
    }

    public PayLinkSdkBuilder setPayerName(String payerName) {
        this.payerName = payerName;
        return this;
    }

    public PayLinkSdkBuilder setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
        return this;
    }

    public PayLinkSdkBuilder setNotification(String notification) {
        this.notification = notification;
        return this;
    }

    public PayLinkSdkBuilder setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public PayLinkSdkBuilder setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public PayLinkSdkBuilder setAmountTransaction(String amountTransaction) {
        this.amountTransaction = amountTransaction;
        return this;
    }

    public PayLinkSdkBuilder setNumberOfPayment(String numberOfPayment) {
        this.numberOfPayment = numberOfPayment;
        return this;
    }

    public PayLinkSdkBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public PayLinkSdkBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public void initiateOrder() {
        PayLinkFragmentPresenter presenter = new PayLinkFragmentPresenter();
        presenter.setCallback(callback); // pass app's callback
        presenter.attachView(payLinkFragmentView);  // still optional, if using internal SDK UI
        presenter.initiateOrder(
                selectedTerminal, merchantId, merchantSecureHash, selectedCurrency, currencyName,
                dateExpire, payerName, notificationMethod, notification,
                referenceNumber, amount, amountTransaction, numberOfPayment, image, message);
    }
}
