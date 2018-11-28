package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvokeResponseModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("tx_id")
    @Expose
    private String txId;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

}
