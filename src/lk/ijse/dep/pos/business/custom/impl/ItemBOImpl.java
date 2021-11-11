package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.entity.Item;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            itemDAO.save(new Item(item.getIcode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getQtyOnHand(),
                    0.00,0.00));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            itemDAO.update(new Item(item.getIcode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),item.getShout(),item.getBalance(),item.getVary()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteItem(String itemCode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            orderDetailDAO.setSession(session);
            session.beginTransaction();
            if (orderDetailDAO.existsByItemCode(itemCode)) {
                throw new AlreadyExistsInOrderException("Item already exists in an order, hence unable to delete");
            }
            itemDAO.delete(itemCode);
            session.getTransaction().commit();
        }
    }


    @Override
    public List<ItemDTO> findSearchItems(String value) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            List<Item> searchList = itemDAO.getSearchList(value);
            session.getTransaction().commit();
            List<ItemDTO> dtos = new ArrayList<>();
            for (Item item : searchList) {
                dtos.add(new ItemDTO(item.getIcode(),
                        item.getDescription(),
                        item.getUnitPrice(),
                        item.getQtyOnHand(), item.getShout(),15,16));
            }

            return dtos;
        }
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            List<Item> allItems = itemDAO.findAll();
            List<ItemDTO> dtos = new ArrayList<>();
            for (Item item : allItems) {
                dtos.add(new ItemDTO(item.getIcode(),
                        item.getDescription(),
                        item.getUnitPrice(),
                        item.getQtyOnHand(), item.getShout(), item.getShout()-(item.getShout()- item.getQtyOnHand()),0));
            }
            session.getTransaction().commit();
            return dtos;
        }
    }


    @Override
    public String getLastItemCode() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            String lastItemCode = itemDAO.getLastItemCode();
            session.getTransaction().commit();
            return lastItemCode;
        }
    }

    @Override
    public double findMinQty(String pack, String pack1, String pack2, String pack3, String pack4) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            double minQty = itemDAO.findMinQty(pack, pack1, pack2, pack3, pack4);
            session.getTransaction().commit();
            return minQty;
        }
    }


    @Override
    public String findItemBydes(String iDesc) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            String icode = itemDAO.findItemByDes(iDesc);
            session.getTransaction().commit();
            return icode;
        }
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            Item item = itemDAO.find(itemCode);
            session.getTransaction().commit();
            return new ItemDTO(item.getIcode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(), item.getShout(),13,14);
        }
    }

    @Override
    public List<String> getAllItemCodes() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            List<Item> allItems = itemDAO.findAll();
            session.getTransaction().commit();
            List<String> codes = new ArrayList<>();
            for (Item item : allItems) {
                codes.add(item.getIcode());
            }
            return codes;
        }
    }
}
