package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.business.custom.PackBO;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.*;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;
import lk.ijse.dep.pos.entity.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);
    private PackDAO packDAO = DAOFactory.getInstance().getDAO(DAOTypes.PACK);
    private PackBO packBO = BOFactory.getInstance().getBO(BOTypes.PACK);

    @Override
    public int getLastOrderId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();
            int lastOrderId = orderDAO.getLastOrderId();
            session.getTransaction().commit();
            return lastOrderId;
        }
    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            orderDAO.setSession(session);
            customerDAO.setSession(session);
            orderDetailDAO.setSession(session);
            packDAO.setSession(session);
            session.beginTransaction();



            for (OrderDetailDTO od : order.getOrderDetails()) {
  /*
                List<Pack> byCode = packDAO.findByCode(od.getCode());
                System.out.println(byCode);
                for (Pack bcode1 : byCode) {
                    bcode1.setQtyOnHand(bcode1.getQtyOnHand() - od.getQty());
                    List<String> allCodes = packBO.getAllCodes(od.getCode());
                    for (String code : allCodes) {

                        packDAO.updateQty(od.getQty(), code);
                    }*/
                }
                int oId = order.getId();
                Date onDate = order.getDate();
                orderDAO.save(new Order(oId, onDate,
                        session.load(Customer.class, order.getCustomerId()), order.getDisc()));
/*


                for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
                    orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(),
                            orderDetail.getQty(), orderDetail.getUnitPrice(),orderDetail.getIcategory(),orderDetail.getIcategory2(),orderDetail.getTodate()));

                    List<String> allPackNo = packDAO.findAllPackNo(orderDetail.getCode());
                    for (String pcode:allPackNo) {
                        System.out.println(pcode);
                        packDAO.updateQty(orderDetail.getQty(),pcode);
                    }

*/



                session.getTransaction().commit();


            }

            //Original Code

        
    }

    @Override
    public void update(OrderDTO orderDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();
            orderDAO.update(new Order(orderDTO.getId(), orderDTO.getDate(), session.load(Customer.class, orderDTO.getCustomerId()),
                    orderDTO.getDisc()));


        }
    }


    public List<OrderDTO2> getOrderInfo(String itemcode, java.sql.Date vfrom, java.sql.Date vto) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();
            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo(itemcode, vfrom, vto);
            session.getTransaction().commit();
            List<OrderDTO2> dtos = new ArrayList<>();
            for (CustomEntity info : ordersInfo) {
     //           dtos.add(new OrderDTO2(info.getOrderDate(), info.getItemCode(), info.getDescription(), info.getQty(), info.getUnitPrice()));
            }
            return dtos;
        }
    }

    @Override
    public List<OrderDTO2> getOrderInfo1(String itemcode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();
            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo1(itemcode);
            session.getTransaction().commit();
            List<OrderDTO2> dtos = new ArrayList<>();
            for (CustomEntity info : ordersInfo) {
           //     dtos.add(new OrderDTO2(info.getOrderDate(), info.getItemCode(), info.getDescription(), info.getQty(), info.getUnitPrice()));
            }
            return dtos;
        }
    }

    @Override
    public List<OrderDTO2> getOrderInfo3(java.sql.Date vfrom, java.sql.Date vto) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();
            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo3(vfrom, vto);
            session.getTransaction().commit();
            List<OrderDTO2> dtos = new ArrayList<>();
            for (CustomEntity info : ordersInfo) {
             //   dtos.add(new OrderDTO2(info.getOrderDate(), info.getItemCode(), info.getDescription(), info.getQty(), info.getUnitPrice()));
            }
            return dtos;
        }
    }


    @Override
    public List<OrderDTO2> getOrderInfo4(String orderId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();
            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo4(orderId);
            session.getTransaction().commit();
            List<OrderDTO2> dtos = new ArrayList<>();
            for (CustomEntity info : ordersInfo) {
           //     dtos.add(new OrderDTO2(info.getOrderDate(), info.getItemCode(), info.getDescription(), info.getQty(), info.getUnitPrice()));
            }
            return dtos;
        }
    }


    @Override
    public void deleteOrderDetail(OrderDetailPK orderPK) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDetailDAO.setSession(session);
            session.beginTransaction();
            orderDetailDAO.delete(orderPK);
            session.getTransaction().commit();
        }
    }

    @Override
    public Double getDisDetail(int orderId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();
            Order order = orderDAO.find(orderId);
            double disc = order.getDisc();
            session.getTransaction().commit();
            return disc;

        }
    }

    @Override
    public void updateDis(int orderId, double disc) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();
            Order orderData = orderDAO.find(orderId);
            orderData.setDisc(disc);
            orderDAO.update(orderData);
            session.getTransaction().commit();


        }
    }


}
