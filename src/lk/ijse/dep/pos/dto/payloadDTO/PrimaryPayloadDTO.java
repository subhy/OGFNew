package lk.ijse.dep.pos.dto.payloadDTO;



import java.util.List;


public class PrimaryPayloadDTO {
    String AppCode;
    String PropertyCode;
    String ClientID;
    String ClientSecret;
    String POSInterfaceCode;
    String BatchCode;
    List<PosSalesPayloadDTO> PosSales;

    public PrimaryPayloadDTO() {
    }

    public PrimaryPayloadDTO(String appCode, String propertyCode, String clientID, String clientSecret, String POSInterfaceCode, String batchCode, List<PosSalesPayloadDTO> posSales) {
        AppCode = appCode;
        PropertyCode = propertyCode;
        ClientID = clientID;
        ClientSecret = clientSecret;
        this.POSInterfaceCode = POSInterfaceCode;
        BatchCode = batchCode;
        PosSales = posSales;
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

    public String getBatchCode() {
        return BatchCode;
    }

    public void setBatchCode(String batchCode) {
        BatchCode = batchCode;
    }

    public List<PosSalesPayloadDTO> getPosSales() {
        return PosSales;
    }

    public void setPosSales(List<PosSalesPayloadDTO> posSales) {
        PosSales = posSales;
    }
}