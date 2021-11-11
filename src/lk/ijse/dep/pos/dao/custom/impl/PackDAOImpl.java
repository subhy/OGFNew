package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.PackDAO;
import lk.ijse.dep.pos.dto.PackDTO;
import lk.ijse.dep.pos.entity.Pack;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class PackDAOImpl extends CrudDAOImpl<Pack, String> implements PackDAO {


    @Override
    public List<String> findAllPackNo(String code) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT code FROM Pack WHERE packNo=?1");
        nativeQuery.setParameter(1, code);

        nativeQuery.getResultList();
        return (List<String>) nativeQuery.getResultList();
    }

    @Override
    public void updateQty(double qty, String code) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("UPDATE Pack SET qtyOnHand-=?1 WHERE code=?2");

        nativeQuery.setParameter(1, qty);
        nativeQuery.setParameter(2, code);

    }


    @Override
    public List<PackDTO> findSearchItem(String val) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM Pack WHERE code=?1");
        nativeQuery.setParameter(1, val);


        return   nativeQuery.getResultList();
    }


}
