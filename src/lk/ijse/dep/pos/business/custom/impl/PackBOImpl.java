package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.PackBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.dao.custom.PackDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.PackDTO;
import lk.ijse.dep.pos.entity.Pack;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class PackBOImpl implements PackBO {

    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    private PackDAO packDAO = DAOFactory.getInstance().getDAO(DAOTypes.PACK);


    @Override
    public void savePack(PackDTO packDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            packDAO.setSession(session);
            session.beginTransaction();
            packDAO.save(new Pack(packDTO.getCode(),
                    packDTO.getPackName(),
                    packDTO.getPack01(),
                    packDTO.getPack02(),
                    packDTO.getPack03(),
                    packDTO.getPack04(),
                    packDTO.getPack05(),
                    packDTO.getUnitPrice()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updatePack(PackDTO pack) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            packDAO.setSession(session);
            session.beginTransaction();
            packDAO.update(new Pack(pack.getCode(),
                    pack.getPackName(),
                    pack.getPack01(),
                    pack.getPack02(),
                    pack.getPack03(),
                    pack.getPack04(),
                    pack.getPack05(),
                    pack.getUnitPrice()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deletePack(String  icode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            packDAO.setSession(session);
            session.beginTransaction();
            packDAO.delete(icode);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<PackDTO> findAllItems() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            packDAO.setSession(session);
            session.beginTransaction();
            List<Pack> searchItem = packDAO.findAll();
            session.getTransaction().commit();
            List<PackDTO> dtos = new ArrayList<>();
            for (Pack pack : searchItem) {
                dtos.add(new PackDTO(pack.getCode(),
                        pack.getPackName(),
                        pack.getPack01(),
                        pack.getPack02(),
                        pack.getPack03(),
                        pack.getPack04(),
                        pack.getPack05(),
                        pack.getUnitPrice()));
            }

            return dtos;
        }
    }

    @Override
    public PackDTO findItem(String iCode) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            packDAO.setSession(session);
            session.beginTransaction();
            Pack pack = packDAO.find(iCode);
            session.getTransaction().commit();
            return new PackDTO(pack.getCode(),
                    pack.getPackName(),
                    pack.getPack01(),
                    pack.getPack02(),
                    pack.getPack03(),
                    pack.getPack04(),
                    pack.getPack05(),
                    pack.getUnitPrice());
        }
    }

    @Override
    public List<PackDTO> findSearchItems(String val) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            packDAO.setSession(session);
            session.beginTransaction();
            List<PackDTO> searchItem = packDAO.findSearchItem(val);
            session.getTransaction().commit();
            List<PackDTO> dtos = new ArrayList<>();
            for (PackDTO pack : searchItem) {
                dtos.add(new PackDTO(pack.getCode(),
                        pack.getPackName(),
                        pack.getPack01(),
                        pack.getPack02(),
                        pack.getPack03(),
                        pack.getPack04(),
                        pack.getPack05(),
                        pack.getUnitPrice()));
            }

            return dtos;
        }
    }



    @Override
    public List<String> getAllCodes(String code) throws Exception {
        try (Session session=HibernateUtil.getSessionFactory().openSession()){
            packDAO.setSession(session);
            session.beginTransaction();
          List<String> allPackNo=  packDAO.findAllPackNo(code);
          session.getTransaction().commit();
          return allPackNo;
        }


    }

}
