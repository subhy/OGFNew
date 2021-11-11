package lk.ijse.dep.pos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class ItemPayload {
  //  @Id
    String ItemDesc;
    String ItemAmt;
    String  ItemDiscountAmt;

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public String getItemAmt() {
        return ItemAmt;
    }

    public void setItemAmt(String itemAmt) {
        ItemAmt = itemAmt;
    }

    public String getItemDiscountAmt() {
        return ItemDiscountAmt;
    }

    public void setItemDiscountAmt(String itemDiscountAmt) {
        ItemDiscountAmt = itemDiscountAmt;
    }
}
