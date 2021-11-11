package lk.ijse.dep.pos.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class ODetails implements SuperEntity{

    @Id
    private int oNo;
    private Date orderDate;
    private int orderId;
    private int orderNo;
    private String iCode;
    private String iName;
    private double qty;
    private double uPrice;
    private String pMethod;
    private String uId;

    public ODetails() {
    }

    public ODetails(int oNo, Date orderDate, int orderId, int orderNo, String iCode,String iName, double qty, double uPrice, String pMethod, String uId) {
        this.oNo = oNo;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.iCode = iCode;
        this.iName=iName;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getiCode() {
        return iCode;
    }

    public void setiCode(String iCode) {
        this.iCode = iCode;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
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
        return "ODetails{" +
                "oNo=" + oNo +
                ", orderDate=" + orderDate +
                ", orderId=" + orderId +
                ", orderNo=" + orderNo +
                ", iCode='" + iCode + '\'' +
                ", iName='" + iName + '\'' +
                ", qty=" + qty +
                ", uPrice=" + uPrice +
                ", pMethod='" + pMethod + '\'' +
                ", uId='" + uId + '\'' +
                '}';
    }
}
