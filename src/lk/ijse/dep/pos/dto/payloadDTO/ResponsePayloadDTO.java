package lk.ijse.dep.pos.dto.payloadDTO;

public class ResponsePayloadDTO {

    String batchCode;
    String returnStatus;
    String recordsReceived;
    String recordsImported;
    String errorDetails;
    String defectiveRowNos;

    public ResponsePayloadDTO() {
    }

    public ResponsePayloadDTO(String batchCode, String returnStatus, String recordsReceived, String recordsImported, String errorDetails, String defectiveRowNos) {
        this.batchCode = batchCode;
        this.returnStatus = returnStatus;
        this.recordsReceived = recordsReceived;
        this.recordsImported = recordsImported;
        this.errorDetails = errorDetails;
        this.defectiveRowNos = defectiveRowNos;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getRecordsReceived() {
        return recordsReceived;
    }

    public void setRecordsReceived(String recordsReceived) {
        this.recordsReceived = recordsReceived;
    }

    public String getRecordsImported() {
        return recordsImported;
    }

    public void setRecordsImported(String recordsImported) {
        this.recordsImported = recordsImported;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getDefectiveRowNos() {
        return defectiveRowNos;
    }

    public void setDefectiveRowNos(String defectiveRowNos) {
        this.defectiveRowNos = defectiveRowNos;
    }
}
