package lk.ijse.dep.pos.business;

import lk.ijse.dep.pos.business.custom.impl.OrderDetailBOImpl;
import lk.ijse.dep.pos.business.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        return (boFactory == null)? (boFactory = new BOFactory()): boFactory;
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case ITEM:
                return (T) new ItemBOImpl();
            case ORDER:
                return (T) new OrderBOImpl();
            case TRANSDETAIL:
                return (T) new TransDetailBOImpl();
                case GENACC:
                return (T) new GenAccBOImpl();
            case PURCHASE:
                return (T) new PurchaseBOImpl();
            case PURCHASEORDER:
                return (T) new PurchaseOrderBOImpl();
            case TRANSACTIONDETAIL:
                return (T) new transactiondetailBOImpl();

            case PACK:
                return (T) new PackBOImpl();
            case ORDERDETAIL:
                return (T) new OrderDetailBOImpl();

            case ODETAIL:
                return (T) new ODetailBOImpl();
            case ORDERLIST:
                return (T) new OrderListBOImpl();
            default:
                return null;
        }
    }

}
