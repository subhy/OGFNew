package lk.ijse.dep.pos.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item implements SuperEntity {

    @Id
    private String icode;
    private String description;
    private double unitPrice;
    private double qtyOnHand;
    private double shout;
    private double balance;
    private double vary;



    public Item() {
    }

    public Item(String icode, String description, double unitPrice, double qtyOnHand, double shout, double balance, double vary) {
        this.icode = icode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.shout = shout;
        this.balance = balance;
        this.vary = vary;
    }

    public String getIcode() {
        return icode;
    }

    public void setIcode(String icode) {
        this.icode = icode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(double qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getShout() {
        return shout;
    }

    public void setShout(double shout) {
        this.shout = shout;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getVary() {
        return vary;
    }

    public void setVary(double vary) {
        this.vary = vary;
    }

    @Override
    public String toString() {
        return "Item{" +
                "icode='" + icode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", shout=" + shout +
                ", balance=" + balance +
                ", vary=" + vary +
                '}';
    }
}
