package io.paysky.upg.data.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitiateOrderResponse implements Serializable {


    @Expose
    @SerializedName("OrderURL")
    private String orderurl;
    @Expose
    @SerializedName("OrderRefId")
    private String orderrefid;
    @Expose
    @SerializedName("OrderId")
    private int orderid;
    @Expose
    @SerializedName("Success")
    private boolean success;
    @Expose
    @SerializedName("Message")
    private String message;

    public String getOrderurl() {
        return orderurl;


    }

    public void setOrderurl(String orderurl) {
        this.orderurl = orderurl;
    }

    public String getOrderrefid() {
        return orderrefid;
    }

    public void setOrderrefid(String orderrefid) {
        this.orderrefid = orderrefid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
