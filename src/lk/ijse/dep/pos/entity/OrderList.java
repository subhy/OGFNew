package lk.ijse.dep.pos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderList implements SuperEntity {
    @Id
    String oNo;
    int ReceiptNo;
    String ItemDesc;
    Double ItemAmt;
    Double ItemDiscountAmt;

    public OrderList() {
    }

    public OrderList(String oNo, int receiptNo, String itemDesc, Double itemAmt, Double itemDiscountAmt) {
        this.oNo = oNo;
        ReceiptNo = receiptNo;
        ItemDesc = itemDesc;
        ItemAmt = itemAmt;
        ItemDiscountAmt = itemDiscountAmt;
    }

    public String getoNo() {
        return oNo;
    }

    public void setoNo(String oNo) {
        this.oNo = oNo;
    }

    public int getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(int receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public Double getItemAmt() {
        return ItemAmt;
    }

    public void setItemAmt(Double itemAmt) {
        ItemAmt = itemAmt;
    }

    public Double getItemDiscountAmt() {
        return ItemDiscountAmt;
    }

    public void setItemDiscountAmt(Double itemDiscountAmt) {
        ItemDiscountAmt = itemDiscountAmt;
    }

    @Override
    public String toString() {
        return "ItemPayload{" +
                "oNo=" + oNo +
                ", ReceiptNo=" + ReceiptNo +
                ", ItemDesc='" + ItemDesc + '\'' +
                ", ItemAmt=" + ItemAmt +
                ", ItemDiscountAmt=" + ItemDiscountAmt +
                '}';
    }
}