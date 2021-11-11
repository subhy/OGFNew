package lk.ijse.dep.pos.dto.payloadDTO;

import java.util.List;

public class PosSalesPayloadDTO {
    String PropertyCode;
    String POSInterfaceCode;
    String ReceiptDate;
    String ReceiptTime;
    String ReceiptNo;
    String NoOfItems;
    String SalesCurrency;
    String TotalSalesAmtB4Tax;
    String TotalSalesAmtAfterTax;
    String SalesTaxRate;
    String ServiceChargeAmt;
    String PaymentAmt;
    String PaymentCurrency;
    String PaymentMethod;
    String SalesType;
    List<ItemPayloadDTO> Items;

    public String getPropertyCode() {
        return PropertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        PropertyCode = propertyCode;
    }

    public String getPOSInterfaceCode() {
        return POSInterfaceCode;
    }

    public void setPOSInterfaceCode(String POSInterfaceCode) {
        this.POSInterfaceCode = POSInterfaceCode;
    }

    public String getReceiptDate() {
        return ReceiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        ReceiptDate = receiptDate;
    }

    public String getReceiptTime() {
        return ReceiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        ReceiptTime = receiptTime;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getNoOfItems() {
        return NoOfItems;
    }

    public void setNoOfItems(String noOfItems) {
        NoOfItems = noOfItems;
    }

    public String getSalesCurrency() {
        return SalesCurrency;
    }

    public void setSalesCurrency(String salesCurrency) {
        SalesCurrency = salesCurrency;
    }

    public String getTotalSalesAmtB4Tax() {
        return TotalSalesAmtB4Tax;
    }

    public void setTotalSalesAmtB4Tax(String totalSalesAmtB4Tax) {
        TotalSalesAmtB4Tax = totalSalesAmtB4Tax;
    }

    public String getTotalSalesAmtAfterTax() {
        return TotalSalesAmtAfterTax;
    }

    public void setTotalSalesAmtAfterTax(String totalSalesAmtAfterTax) {
        TotalSalesAmtAfterTax = totalSalesAmtAfterTax;
    }

    public String getSalesTaxRate() {
        return SalesTaxRate;
    }

    public void setSalesTaxRate(String salesTaxRate) {
        SalesTaxRate = salesTaxRate;
    }

    public String getServiceChargeAmt() {
        return ServiceChargeAmt;
    }

    public void setServiceChargeAmt(String serviceChargeAmt) {
        ServiceChargeAmt = serviceChargeAmt;
    }

    public String getPaymentAmt() {
        return PaymentAmt;
    }

    public void setPaymentAmt(String paymentAmt) {
        PaymentAmt = paymentAmt;
    }

    public String getPaymentCurrency() {
        return PaymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        PaymentCurrency = paymentCurrency;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getSalesType() {
        return SalesType;
    }

    public void setSalesType(String salesType) {
        SalesType = salesType;
    }

    public List<ItemPayloadDTO> getItems() {
        return Items;
    }

    public void setItems(List<ItemPayloadDTO> items) {
        this.Items = items;
    }
}
