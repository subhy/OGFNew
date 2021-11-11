package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.entity.OrderList;

public interface OrderListDAO extends CrudDAO<OrderList, Integer> {

    int getLastOrderId() throws Exception;
}
