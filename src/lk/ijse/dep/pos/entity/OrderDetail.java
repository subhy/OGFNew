package lk.ijse.dep.pos.entity;

import javax.persistence.*;

@Entity
public class OrderDetail implements SuperEntity{

    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    private double qty;
    @Column(name="unit_price")
    private double unitPrice;
    private String icategory;
    private String icategory2;
    private String todate;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name="order_id",referencedColumnName = "id", insertable = false, updatable = false)
    private Order order;


    public OrderDetail() {
    }

    public OrderDetail(OrderDetailPK orderDetailPK, double qty, double unitPrice,String icategory,String icategory2,String todate) {
        this.orderDetailPK = orderDetailPK;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.icategory=icategory;
        this.icategory2=icategory2;
        this.todate=todate;

    }

    public OrderDetail(int orderId, String itemCode, double qty, double unitPrice,String icategory,String icategory2,String todate) {
        this.orderDetailPK = new OrderDetailPK(orderId, itemCode);
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.icategory=icategory;
        this.icategory2=icategory2;
        this.todate=todate;
    }

    public OrderDetailPK getOrderDetailPK() {
        return orderDetailPK;
    }

    public void setOrderDetailPK(OrderDetailPK orderDetailPK) {
        this.orderDetailPK = orderDetailPK;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getIcategory() {
        return icategory;
    }

    public void setIcategory(String icategory) {
        this.icategory = icategory;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Order getOrder() {
        return order;
    }

    // Cascading purposes...
    public void setOrder(Order order) {
        this.order = order;
    }



    public String getIcategory2() {
        return icategory2;
    }

    public void setIcategory2(String icategory2) {
        this.icategory2 = icategory2;
    }
}
