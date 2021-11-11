package lk.ijse.dep.pos.dto;

import java.sql.Date;

public class OrderDetailDTO {

    private int oNo;
    private Date todate;
    private String orderId;
    private String orderNo;
    private String iCode;
    private double qty;
    private double uPrice;
    private String pMethod;
    private String uId;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int oNo, Date todate, String orderId, String orderNo, String iCode, double qty, double uPrice, String pMethod, String uId) {
        this.oNo = oNo;
        this.todate = todate;
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.iCode = iCode;
        this.qty = qty;
        this.uPrice = uPrice;
        this.pMethod = pMethod;
        this.uId = uId;
    }



    public int getoNo() {
        return oNo;
    }

    public void setoNo(int oNo) {
        this.oNo = oNo;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getiCode() {
        return iCode;
    }

    public void setiCode(String iCode) {
        this.iCode = iCode;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getuPrice() {
        return uPrice;
    }

    public void setuPrice(double uPrice) {
        this.uPrice = uPrice;
    }

    public String getpMethod() {
        return pMethod;
    }

    public void setpMethod(String pMethod) {
        this.pMethod = pMethod;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "oNo=" + oNo +
                ", todate=" + todate +
                ", orderId='" + orderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", iCode='" + iCode + '\'' +
                ", qty=" + qty +
                ", uPrice=" + uPrice +
                ", pMethod='" + pMethod + '\'' +
                ", uId='" + uId + '\'' +
                '}';
    }
}
