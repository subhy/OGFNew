package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.transactiondetailBO;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.transactiondetailDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.TransDetailDTO;
import lk.ijse.dep.pos.dto.transactiondetailDTO;
import lk.ijse.dep.pos.entity.TransDetail;
import lk.ijse.dep.pos.entity.transactiondetail;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class transactiondetailBOImpl implements transactiondetailBO {
    private transactiondetailDAO transDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.TRANSACTIONDETAIL);


    @Override
    public String findTransDetailByDateCategory(String accNo, String dpDate, String accDesc) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            String transNo = transDetailDAO.findTransDetailByDateCate(accNo, dpDate, accDesc);
            session.getTransaction().commit();
            return transNo;

        }
    }

    @Override
    public List<transactiondetailDTO> findTransDetail(String transNo) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            transactiondetail transDetail = transDetailDAO.find(transNo);
            List<transactiondetailDTO> items = new ArrayList<>();
            items.add(new transactiondetailDTO(transDetail.getTransNo(), transDetail.getTransDate(),
                    transDetail.getTransAccNo(), transDetail.getTransCate(), transDetail.getTransSubCate(),
                    transDetail.getTransDesc(), transDetail.getTransType(), transDetail.getTransAmount(), transDetail.getTransUserId(),transDetail.getTransUserName()));
            session.getTransaction().commit();
            return items;
        }
    }

    @Override
    public void updateTransDetail(transactiondetailDTO transDetail) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            transDetailDAO.update(new transactiondetail(transDetail.getTransNo(), transDetail.getTransDate(),
                    transDetail.getTransAccNo(), transDetail.getTransCate(), transDetail.getTransSubCate(),
                    transDetail.getTransDesc(), transDetail.getTransType(), transDetail.getTransAmount(), transDetail.getTransUserId(),transDetail.getTransUserName()));
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean existsByCateDate(String oilSale, String toDate, String pMethod, String subCate) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            boolean existData = transDetailDAO.existsByCateDate(oilSale, toDate, pMethod, subCate);

            session.getTransaction().commit();
            return existData;
        }
    }

    @Override
    public Double getTotal(String sDateFrom, String sDateTo, String pType, String subCate) throws Exception {
        Double transAmount = 0.00;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            transAmount = transDetailDAO.findTotal(sDateFrom, sDateTo, pType, subCate);

            session.getTransaction().commit();
            return transAmount;
        }
    }

    @Override
    public Integer getLastOrderId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            Integer lastOrderId = transDetailDAO.getLastOrderId();
            session.getTransaction().commit();
            return lastOrderId;
        }

    }

    @Override
    public void saveTransDetail(transactiondetailDTO transDetail) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            transDetailDAO.save(new transactiondetail(transDetail.getTransNo(), transDetail.getTransDate(),
                    transDetail.getTransAccNo(), transDetail.getTransCate(), transDetail.getTransSubCate(),
                    transDetail.getTransDesc(), transDetail.getTransType(), transDetail.getTransAmount(), transDetail.getTransUserId(),transDetail.getTransUserName()));
            session.getTransaction().commit();
        }
    }

    @Override
    public String getTransaNoByOrderId(String orderId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            String transNo = transDetailDAO.getTransNoByOrderId(orderId);
            session.getTransaction().commit();
            if (transNo == null) {
                return "null";
            } else {
                return transNo;
            }
        }
    }

    @Override
    public void deleteTransDetail(String transNo) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            transDetailDAO.delete(transNo);
            session.getTransaction().commit();

        }
    }

    @Override
    public Double findTotalAndDisc(String oId, String oilSale, String toDate, String pMethod, String subCate) throws Exception {
        Double transAmount = 0.00;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transDetailDAO.setSession(session);
            session.beginTransaction();
            transAmount = transDetailDAO.findTotalAndDisc(oId, oilSale, toDate, pMethod, subCate);

            session.getTransaction().commit();
            return transAmount;
        }
    }
}
