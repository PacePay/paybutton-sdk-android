package io.paysky.ui.paylink.mvp;

import java.math.BigInteger;
import io.paysky.data.model.request.InitiateOrderRequest;
import io.paysky.data.model.response.InitiateOrderResponse;
import io.paysky.data.network.ApiConnection;
import io.paysky.data.network.ApiInterface;
import io.paysky.ui.paylink.PayLinkCallback;
import io.paysky.util.HashGenerator;
import io.paysky.util.NumberUtil;
import retrofit2.Call;
import retrofit2.Callback;

public class PayLinkFragmentManager {
    private PayLinkCallback callback;

    public void setCallback(PayLinkCallback callback) {
        this.callback = callback;
    }

    void initiateOrder(String selectedTerminal, String merchantId, String merchantSecureHash,
                       String selectedCurrency, String currencyName, String dateExpire, String payerName,
                       String notificationMethod, String notification, String referenceNumber, String amount,
                       String amountTransaction, String numberOfPayment, String image, String message) {

        InitiateOrderRequest request = new InitiateOrderRequest();
        request.setTerminalId(selectedTerminal);
        request.setMerchantId(merchantId);
        request.setSecureHash(HashGenerator.encode(merchantSecureHash, request.getDateTimeLocalTrxn(), merchantId, selectedTerminal));
        request.setCurrency(selectedCurrency);
        request.setCurrencyName(currencyName);
        request.setExpiryDateTime(dateExpire);
        request.setPayerName(payerName);
        request.setNotificationMethod(notificationMethod);
        request.setNotificationValue(notification);
        request.setMerchantReference(referenceNumber);
        request.setAmountTrxn(new BigInteger(amountTransaction) + "");
        if (amount != null) request.setAmount(NumberUtil.removeCommaInString(amount));
        request.setMessage(message);

        try {
            request.setMaxNumberOfPayment((numberOfPayment.isEmpty() || Long.parseLong(numberOfPayment) == 0) ? "1" : numberOfPayment);
        } catch (Exception e) {
            request.setMaxNumberOfPayment("1");
        }

        ApiInterface apiInterface = ApiConnection.getPayLinkInterface();

        apiInterface.initiateOrder(request).enqueue(new Callback<InitiateOrderResponse>() {
            @Override
            public void onResponse(Call<InitiateOrderResponse> call, retrofit2.Response<InitiateOrderResponse> response) {
                if (callback == null) return;

                InitiateOrderResponse body = response.body();
                if (body != null) {
                    if (body.isSuccess()) {
                        callback.onSuccess(request, body);
                    } else {
                        callback.onError(body.getMessage());
                    }
                } else {
                    callback.onError("Response body is null");
                }
            }

            @Override
            public void onFailure(Call<InitiateOrderResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onError(t.getMessage());
                }
            }
        });
    }
}
