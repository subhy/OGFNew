package lk.ijse.dep.pos.util;

public class ItemTM implements Cloneable{

    private String icode;
    private String description;
    private double qtyOnHand;
    private double shout;
    private double balance;
    private double vary;


    public ItemTM() {
    }

    public ItemTM(String icode, String description, double qtyOnHand, double shout, double balance, double vary) {
        this.icode = icode;
        this.description = description;
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
        return "ItemTM{" +
                "icode='" + icode + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", shout=" + shout +
                ", balance=" + balance +
                ", vary=" + vary +
                '}';
    }
}
