package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderListBO;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.OrderListDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.OrderListDTO;
import lk.ijse.dep.pos.entity.OrderList;
import org.hibernate.Session;

public class OrderListBOImpl implements OrderListBO {

    private OrderListDAO itemPayloadDAO=DAOFactory.getInstance().getDAO(DAOTypes.ORDERLIST);
    @Override
    public void saveItem(OrderListDTO orderItem) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemPayloadDAO.setSession(session);
            session.beginTransaction();
            itemPayloadDAO.save(new OrderList(orderItem.getoNo(),orderItem.getReceiptNo(),orderItem.getItemDesc(),orderItem.getItemAmt(),orderItem.getItemDiscountAmt()));
            session.getTransaction().commit();
        }
    }

    @Override
    public int getLastOrderId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemPayloadDAO.setSession(session);
            session.beginTransaction();
            int lastOrderId = itemPayloadDAO.getLastOrderId();
            session.getTransaction().commit();
            return lastOrderId;
        }
    }
}
