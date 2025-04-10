package io.paysky.ui.mvp.paylinkfragment;

import com.example.paybutton.R;

import java.math.BigInteger;

import io.paysky.data.model.request.InitiateOrderRequest;
import io.paysky.data.model.response.InitiateOrderResponse;
import io.paysky.data.network.ApiConnection;
import io.paysky.data.network.ApiInterface;
import io.paysky.util.HashGenerator;
import io.paysky.util.NumberUtil;
import io.paysky.util.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;

public class PayLinkFragmentManager {
    private PayLinkFragmentView view;

    public void setView(PayLinkFragmentView view) {
        this.view = view;
    }

    void initiateOrder(String selectedTerminal,String merchantId,String merchantSecureHash, String selectedCurrancy, String currencyName
            , String dateExpire, String payerName, String notificationMethod, String notification, String referenceNumber, String amount, String amountTransaction, String numberOfPayment, String Image, String message) {
        view.showProgress(R.string.genrateing_pay_link);
        final InitiateOrderRequest InitiateOrder = new InitiateOrderRequest();
        InitiateOrder.setTerminalId(selectedTerminal);
        InitiateOrder.setMerchantId(merchantId);
        InitiateOrder.setSecureHash(HashGenerator.encode(merchantSecureHash, InitiateOrder.getDateTimeLocalTrxn(), merchantId, selectedTerminal));
        InitiateOrder.setCurrency(selectedCurrancy);
        InitiateOrder.setCurrencyName(currencyName);
        InitiateOrder.setExpiryDateTime(dateExpire);
        InitiateOrder.setPayerName(payerName);
        InitiateOrder.setNotificationMethod(notificationMethod);
        InitiateOrder.setNotificationValue(notification);
        InitiateOrder.setMerchantReference(referenceNumber);
        InitiateOrder.setAmountTrxn(new BigInteger(amountTransaction) + "");
        if (amount != null)
            InitiateOrder.setAmount(NumberUtil.removeCommaInString(amount));
        InitiateOrder.setMessage(message);
        try {
            if (numberOfPayment.isEmpty() || Long.parseLong(numberOfPayment) == 0) {
                InitiateOrder.setMaxNumberOfPayment("1");
            } else {
                InitiateOrder.setMaxNumberOfPayment(numberOfPayment);
            }
        } catch (Exception e) {
            InitiateOrder.setMaxNumberOfPayment("1");
        }
//        if (BuildUtil.isVPOSGroupApp()) {
//            view.showPayLinkDialog(InitiateOrder, null);
//            if (view == null) return;
//            view.dismissProgress();
//            return;
//        }
        ApiInterface apiInterface = ApiConnection.getPayLinkInterface();

        apiInterface.initiateOrder(InitiateOrder)
                .enqueue(new Callback<InitiateOrderResponse>() {
                    @Override
                    public void onResponse(Call<InitiateOrderResponse> call, retrofit2.Response<InitiateOrderResponse> response) {
                        if (view == null) return;
                        view.dismissProgress();
                        if (response.code() == 400) {
                            ToastUtil.showShortToast(view.getContext(), "error");
                        }
                        InitiateOrderResponse body = response.body();
                        if (body != null) {
                            if (body.isSuccess()) {
                                view.showPayLinkDialog(InitiateOrder, body);
                            } else {
                                ToastUtil.showShortToast(view.getContext(), body.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<InitiateOrderResponse> call, Throwable t) {

                    }
                });
    }

    public void hideDataForm() {
        view.hideDataForm();
    }
}
