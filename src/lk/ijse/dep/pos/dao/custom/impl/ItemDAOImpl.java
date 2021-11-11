package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.entity.Item;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class ItemDAOImpl extends CrudDAOImpl<Item, String> implements ItemDAO {

    @Override
    public String getLastItemCode() throws Exception {
        return (String) session.createNativeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1").uniqueResult();
    }

    @Override
    public List<Item> getSearchList(String value) throws Exception {
        NativeQuery<Item> nativeQuery = session.createNativeQuery("SELECT * FROM Item i WHERE i.itemCategory =?1 AND i.itemCategory2 =?2", Item.class);
        System.out.println(value);
        nativeQuery.setParameter(1, value);
        nativeQuery.setParameter(2, value);
        List<Item> resultList = nativeQuery.getResultList();
        return resultList;

    }

    @Override
    public double findMinQty(String pack, String pack1, String pack2, String pack3, String pack4) throws Exception {
        if (pack1.equals("nu") && pack2.equals("nu") && pack3.equals("nu") && pack4.equals("nu")) {
            NativeQuery nativeQuery = session.createNativeQuery("SELECT MIN(qtyOnHand) FROM Item WHERE description= ?1");
            nativeQuery.setParameter(1, pack);

            double minQty = (Double) nativeQuery.uniqueResult();
            return minQty;
        } else if (pack2.equals("nu") && pack3.equals("nu") && pack4.equals("nu")) {
            NativeQuery nativeQuery = session.createNativeQuery("SELECT MIN(qtyOnHand) FROM Item WHERE description= ?1 OR description=?2");
            nativeQuery.setParameter(1, pack);
            nativeQuery.setParameter(2, pack1);

            double minQty = (Double) nativeQuery.uniqueResult();
            return minQty;
        } else if (pack3.equals("nu") && pack4.equals("nu")) {
            NativeQuery nativeQuery = session.createNativeQuery("SELECT MIN(qtyOnHand) FROM Item WHERE description= ?1 OR description=?2 OR description=?3");
            nativeQuery.setParameter(1, pack);
            nativeQuery.setParameter(2, pack1);
            nativeQuery.setParameter(3, pack2);

            double minQty = (Double) nativeQuery.uniqueResult();
            return minQty;
        } else if (pack4.equals("nu")) {
            NativeQuery nativeQuery = session.createNativeQuery("SELECT MIN(qtyOnHand) FROM Item WHERE description= ?1 OR description=?2 OR description=?3 OR description=?4");
            nativeQuery.setParameter(1, pack);
            nativeQuery.setParameter(2, pack1);
            nativeQuery.setParameter(3, pack2);
            nativeQuery.setParameter(4, pack3);

            double minQty = (Double) nativeQuery.uniqueResult();
            return minQty;
        } else {

            NativeQuery nativeQuery = session.createNativeQuery("SELECT MIN(qtyOnHand) FROM Item WHERE description= ?1 OR description=?2 OR description=?3 OR description=?4 OR description=?5");
            nativeQuery.setParameter(1, pack);
            nativeQuery.setParameter(2, pack1);
            nativeQuery.setParameter(3, pack2);
            nativeQuery.setParameter(4, pack3);
            nativeQuery.setParameter(5, pack4);
            double minQty = (Double) nativeQuery.uniqueResult();
            return minQty;
        }
    }


    @Override
    public String findItemByDes(String iDesc) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT icode FROM Item WHERE description= ?1");
        nativeQuery.setParameter(1, iDesc);

        String itemC = nativeQuery.uniqueResult().toString();
        return itemC;
    }
}
