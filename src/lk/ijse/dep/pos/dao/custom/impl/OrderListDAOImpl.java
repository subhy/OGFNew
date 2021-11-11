package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.OrderListDAO;
import lk.ijse.dep.pos.entity.OrderList;

public class OrderListDAOImpl extends CrudDAOImpl<OrderList, Integer> implements OrderListDAO {

    @Override
    public int getLastOrderId() throws Exception {
        Integer i = (Integer) session.createNativeQuery("SELECT ReceiptNo FROM OrderList ORDER BY ReceiptNo DESC LIMIT 1")
                .uniqueResult();

        return (i == null) ? 0 : i;
    }
}
