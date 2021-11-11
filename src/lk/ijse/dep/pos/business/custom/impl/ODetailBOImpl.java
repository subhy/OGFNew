package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.ODetailBO;
import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.business.custom.PackBO;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.*;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.ODetailDTO;
import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;
import lk.ijse.dep.pos.entity.*;
import org.hibernate.Session;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ODetailBOImpl implements ODetailBO {

    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
    private ODetailDAO ODetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ODETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);
    private PackDAO packDAO = DAOFactory.getInstance().getDAO(DAOTypes.PACK);
    private PackBO packBO = BOFactory.getInstance().getBO(BOTypes.PACK);

    @Override
    public int getLastOrderId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ODetailDAO.setSession(session);
            session.beginTransaction();
            int lastOrderId = ODetailDAO.getLastOrderId();
            session.getTransaction().commit();
            return lastOrderId;
        }
    }

    @Override
    public void saveOrder(ODetailDTO detailDTO) throws Exception {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ODetailDAO.setSession(session);
            session.beginTransaction();
            ODetailDAO.save(new ODetails(detailDTO.getoNo(),
                    detailDTO.getOrderDate(),detailDTO.getOrderId(),detailDTO.getOrderNo(),
                    detailDTO.getiCode(),detailDTO.getiName(),detailDTO.getQty(),detailDTO.getuPrice(),detailDTO.getpMethod(),detailDTO.getuId()));
            session.getTransaction().commit();
        }
        }

    @Override
    public void updateOrder(ODetailDTO detailDTO) throws Exception {

    }

    @Override
    public void deleteOrder(int oNo) throws Exception {

    }

    @Override
    public List<ODetailDTO> findAllItems() throws Exception {
        return null;
    }

    @Override
    public ODetailDTO findOrder(int oNo) throws Exception {
        return null;
    }

    @Override
    public List<OrderDTO2> getOrderInfo3(Date dateFrom, Date dateTo) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ODetailDAO.setSession(session);
            session.beginTransaction();
            List<CustomEntity> ordersInfo = ODetailDAO.getOrdersInfo3(dateFrom, dateTo);
            session.getTransaction().commit();
            List<OrderDTO2> dtos = new ArrayList<>();
            for (CustomEntity info : ordersInfo) {
                dtos.add(new OrderDTO2(info.getOrderDate(), info.getiCode(), info.getiName(), info.getQty(), info.getuPrice()));
            }
            return dtos;
        }
    }

    @Override
    public Double findByPMethod(String oId, String pMethod, String dateTo, String dateFrom, String s2) {
        Double transAmount = 0.00;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ODetailDAO.setSession(session);
            session.beginTransaction();
            transAmount = ODetailDAO.findByPMethod(oId, pMethod, dateTo, dateFrom, s2);

            session.getTransaction().commit();

        } catch (Exception e) {

        }
        return transAmount;
    }
    }

