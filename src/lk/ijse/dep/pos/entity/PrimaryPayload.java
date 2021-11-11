package lk.ijse.dep.pos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PrimaryPayload {
    @Id
    String ReceiptNo;
    String BatchCode;
    String AppCode;
    String PropertyCode;
    String ClientID;
    String ClientSecret;
    String POSInterfaceCode;

    public PrimaryPayload() {
    }

    public PrimaryPayload(String receiptNo, String batchCode, String appCode, String propertyCode, String clientID, String clientSecret, String POSInterfaceCode) {
        ReceiptNo = receiptNo;
        BatchCode = batchCode;
        AppCode = appCode;
        PropertyCode = propertyCode;
        ClientID = clientID;
        ClientSecret = clientSecret;
        this.POSInterfaceCode = POSInterfaceCode;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getBatchCode() {
        return BatchCode;
    }

    public void setBatchCode(String batchCode) {
        BatchCode = batchCode;
    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getPropertyCode() {
        return PropertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        PropertyCode = propertyCode;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getClientSecret() {
        return ClientSecret;
    }

    public void setClientSecret(String clientSecret) {
        ClientSecret = clientSecret;
    }

    public String getPOSInterfaceCode() {
        return POSInterfaceCode;
    }

    public void setPOSInterfaceCode(String POSInterfaceCode) {
        this.POSInterfaceCode = POSInterfaceCode;
    }

    @Override
    public String toString() {
        return "PrimaryPayload{" +
                "ReceiptNo='" + ReceiptNo + '\'' +
                ", BatchCode='" + BatchCode + '\'' +
                ", AppCode='" + AppCode + '\'' +
                ", PropertyCode='" + PropertyCode + '\'' +
                ", ClientID='" + ClientID + '\'' +
                ", ClientSecret='" + ClientSecret + '\'' +
                ", POSInterfaceCode='" + POSInterfaceCode + '\'' +
                '}';
    }
}