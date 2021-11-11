package lk.ijse.dep.pos.entity;

import java.sql.Date;

public class CustomEntity implements SuperEntity {

    private Date orderDate;
    private String iCode;
    private String iName;
    private double uPrice;
    private double qty;

    public CustomEntity() {
    }

    public CustomEntity(Date orderDate, String iCode, String iName, double uPrice, double qty) {
        this.orderDate = orderDate;
        this.iCode = iCode;
        this.iName = iName;
        this.uPrice = uPrice;
        this.qty = qty;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public double getuPrice() {
        return uPrice;
    }

    public void setuPrice(double uPrice) {
        this.uPrice = uPrice;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}