package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.PurOrderDetailDAO;
import lk.ijse.dep.pos.entity.PurOrderDetail;

public class PurOrderDetailDAOImpl extends CrudDAOImpl<PurOrderDetail, Integer> implements PurOrderDetailDAO {

    @Override
    public Integer getLastOrderId() throws Exception {
        return (Integer) session.createNativeQuery("SELECT orderId FROM PurOrderDetail ORDER BY orderId DESC LIMIT 1").uniqueResult();

    }
}
