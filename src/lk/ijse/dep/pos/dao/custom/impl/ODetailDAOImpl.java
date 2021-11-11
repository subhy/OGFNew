package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.ODetailDAO;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import lk.ijse.dep.pos.entity.ODetails;
import lk.ijse.dep.pos.entity.Order;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.sql.Date;
import java.util.List;

public class ODetailDAOImpl extends CrudDAOImpl<ODetails, Integer> implements ODetailDAO {
    @Override
    public int getLastOrderId() throws Exception {
        Integer i = (Integer) session.createNativeQuery("SELECT orderId FROM ODetails ORDER BY orderId DESC LIMIT 1")
                .uniqueResult();

        return (i == null) ? 0 : i;
    }

    @Override
    public Double findByPMethod(String oId, String pMethod, String dateTo, String dateFrom, String s2) throws Exception {
        Double transAmount = 0.00;

        NativeQuery nativeQuery = session.createNativeQuery("SELECT SUM(od.qty*od.uPrice) AS totalA FROM ODetails od WHERE od.pMethod=?1 AND od.orderDate=?2");
        nativeQuery.setParameter(1, pMethod);
        nativeQuery.setParameter(2, dateTo);

        transAmount = (Double) nativeQuery.uniqueResult();
        if (transAmount.equals(null)){
            return 0.00;
        }else {
            return transAmount;
        }
    }

    @Override
    public List<CustomEntity> getOrdersInfo3(Date dateFrom, Date dateTo) throws Exception {
      //  NativeQuery nativeQuery = session.createNativeQuery("SELECT DISTINCT(i.code) AS itemCode,i.description AS description,SUM(od.qty) AS qty,i.unitPrice AS unitPrice FROM  `Order` o INNER JOIN OrderDetail od ON o.id = od.order_id INNER JOIN Item i ON od.item_code = i.code WHERE o.date between ?1 and ?2 GROUP BY i.code");
        NativeQuery nativeQuery = session.createNativeQuery("SELECT od.orderDate As orderDate,(od.iCode) AS iCode,od.iName AS iName,SUM(od.qty) AS qty,od.uPrice AS uPrice FROM ODetails od WHERE od.orderDate BETWEEN ?1 AND ?2 GROUP BY od.iCode,od.iName, od.uPrice,od.orderDate ORDER BY od.iCode ASC");
        nativeQuery.setParameter(1,dateFrom);
        nativeQuery.setParameter(2,dateTo);

        Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List<CustomEntity> list = query.list();
        return list;
    }
}


