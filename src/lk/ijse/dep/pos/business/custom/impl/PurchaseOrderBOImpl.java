package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.PurchaseOrderBO;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.PurOrderDetailDAO;
import lk.ijse.dep.pos.dao.custom.PurchaseDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.PurchaseDTO;
import lk.ijse.dep.pos.dto.PurchaseOrderDTO;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.PurOrderDetail;
import org.hibernate.Session;

import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private PurOrderDetailDAO poDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.PURORDERDETAIL);
    private PurchaseDAO purchaseDAO = DAOFactory.getInstance().getDAO(DAOTypes.PURCHASE);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);


    @Override
    public void savePDetail(PurchaseOrderDTO purchaseOrderDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            poDetailDAO.setSession(session);
            itemDAO.setSession(session);
            purchaseDAO.setSession(session);

            session.beginTransaction();
            poDetailDAO.save(new PurOrderDetail(purchaseOrderDTO.getOrderId(),purchaseOrderDTO.getoNo(),purchaseOrderDTO.getiCode(),
                    purchaseOrderDTO.getNoOfItem(),purchaseOrderDTO.getPack(),purchaseOrderDTO.getPurPrice(),
                    purchaseOrderDTO.getDiscount(),purchaseOrderDTO.getMargin()));






                Item item = itemDAO.find(purchaseOrderDTO.getiCode());
                item.setQtyOnHand((item.getQtyOnHand()+ (purchaseOrderDTO.getNoOfItem()*purchaseOrderDTO.getPack())));
                itemDAO.update(item);



            session.getTransaction().commit();
        }
    }

    @Override
    public void updatePDetail() throws Exception {

    }

    @Override
    public void deletePDetail() throws Exception {

    }

    @Override
    public List<PurchaseOrderDTO> findAllPDetail() throws Exception {
        return null;
    }

    @Override
    public PurchaseDTO findPOrder(String pOrderId) throws Exception {
        return null;
    }

    @Override
    public Integer getLastOrderId() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
           poDetailDAO.setSession(session);
           session.beginTransaction();
            Integer lastCustomerId = poDetailDAO.getLastOrderId();
            session.getTransaction().commit();
            return lastCustomerId;
        }
    }
}
