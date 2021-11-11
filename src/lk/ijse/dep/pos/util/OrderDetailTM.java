package lk.ijse.dep.pos.util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;

public class OrderDetailTM {

    private String code;
    private String description;
    private double qty;
    private double unitPrice;
    private double total;
    private Button btnDelete;
    private String icategory;
    private String icategory2;


    public OrderDetailTM(String code, String description, double qty, double unitPrice, double total, Button btnDelete,String icategory,String icategory2) {
        this.setCode(code);
        this.setDescription(description);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
        this.setBtnDelete(btnDelete);
        this.setIcategory(icategory);
        this.setIcategory2(icategory2);

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    public String getIcategory() {
        return icategory;
    }

    public void setIcategory(String icategory) {
        this.icategory = icategory;
    }

    public String getIcategory2() {
        return icategory2;
    }

    public void setIcategory2(String icategory2) {
        this.icategory2 = icategory2;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", btnDelete=" + btnDelete +
                ", icategory='" + icategory + '\'' +
                ", icategory2='" + icategory2 + '\'' +
                '}';
    }
}
