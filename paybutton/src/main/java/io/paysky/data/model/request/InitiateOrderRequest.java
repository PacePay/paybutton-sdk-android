package io.paysky.upg.data.network.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class InitiateOrderRequest extends BaseRequest {

    @SerializedName("CallBackURL")
    String CallBackURL;
    @SerializedName("Currency")
    String Currency;


    @SerializedName("CurrencyName")
    String CurrencyName;

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }

    @SerializedName("ExpiryDateTime")
    String ExpiryDateTime;
    @SerializedName("NotificationMethod")
    String NotificationMethod;
    @SerializedName("NotificationValue")
    String NotificationValue;
    @SerializedName("PayerName")
    String PayerName;
    @SerializedName("MaxNumberOfPayment")
    String MaxNumberOfPayment;
    @SerializedName("Message")
    String Message;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCallBackURL() {
        return CallBackURL;
    }

    public void setCallBackURL(String callBackURL) {
        CallBackURL = callBackURL;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getExpiryDateTime() {
        return ExpiryDateTime;
    }

    public void setExpiryDateTime(String expiryDateTime) {
        ExpiryDateTime = expiryDateTime;
    }

    public String getNotificationMethod() {
        return NotificationMethod;
    }

    public void setNotificationMethod(String notificationMethod) {
        NotificationMethod = notificationMethod;
    }

    public String getNotificationValue() {
        return NotificationValue;
    }

    public void setNotificationValue(String notificationValue) {
        NotificationValue = notificationValue;
    }

    public String getPayerName() {
        return PayerName;
    }

    public void setPayerName(String payerName) {
        PayerName = payerName;
    }

    public String getMaxNumberOfPayment() {
        return MaxNumberOfPayment;
    }

    public void setMaxNumberOfPayment(String maxNumberOfPayment) {
        MaxNumberOfPayment = maxNumberOfPayment;
    }


    @SerializedName("Amount")
    private String Amount;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    String ExpiryString;

    public String getExpiryString() {


        final Calendar calendar = Calendar.getInstance();
//        int arg4 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        int arg5 = Calendar.getInstance().get(Calendar.MINUTE);

//        String time = " "+arg4 + " : " + arg5 + " ";
        return ExpiryString  ;
    }

    public void setExpiryString(String expiryString) {
        ExpiryString = expiryString;
    }
}
