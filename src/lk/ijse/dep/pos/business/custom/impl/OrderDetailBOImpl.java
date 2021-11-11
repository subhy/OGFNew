package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderDetailBO;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.dao.custom.PackDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.entity.OrderDetail;
import org.hibernate.Session;

public class OrderDetailBOImpl implements OrderDetailBO {

    private OrderDetailDAO orderDetailDAO= DAOFactory.getInstance().getDAO(DAOTypes.ORDERDETAIL);


    @Override
    public Double findAllItemQtyByCategory(String itemCategory,String itemCategory2,String todate) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDetailDAO.setSession(session);
            session.beginTransaction();
            double sumOfQty = orderDetailDAO.findAllItemQtyByCategory(itemCategory,itemCategory2, todate);
            session.getTransaction().commit();
            if (String.valueOf(sumOfQty).equals(null)) {
                return 0.0;
            } else {
                 return sumOfQty;
            }
        }
    }

    @Override
    public boolean isExisitsOrder(String itemCategory, String todate) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDetailDAO.setSession(session);
            session.beginTransaction();
            boolean existData = orderDetailDAO.isExisits(itemCategory, todate);

            session.getTransaction().commit();
            System.out.println(existData);
            return existData;
        }
    }
}
