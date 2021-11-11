package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.entity.OrderDetail;
import lk.ijse.dep.pos.entity.OrderDetailPK;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail, OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsByItemCode(String itemCode) throws Exception {
        return session.createNativeQuery("SELECT * FROM OrderDetail WHERE item_code=?1")
                .setParameter(1, itemCode).uniqueResult() != null;
    }

    @Override
    public double findAllItemQtyByCategory(String itemCategory,String itemCategory2, String todate) throws Exception {
   if (itemCategory.equals("No Data")) {
       NativeQuery nativeQuery = session.createNativeQuery("SELECT SUM(qty) FROM OrderDetail WHERE icategory2=?1 AND todate=?2");
       nativeQuery.setParameter(1, itemCategory2);
        nativeQuery.setParameter(2, todate);
       System.out.println("*************************");
       System.out.println(nativeQuery.list().size());
       if (nativeQuery.list().size()==1){
             System.out.println("Ai Null");
             return 0.00;
         }else{
             Double sumQty = (Double) nativeQuery.uniqueResult();
           System.out.println("+++++++++++++");
           System.out.println(nativeQuery.uniqueResult());
             return sumQty;

         }



   }else{
       NativeQuery nativeQuery = session.createNativeQuery("SELECT SUM(qty) FROM OrderDetail WHERE icategory= ?1 OR icategory2=?2 AND todate=?3");
       nativeQuery.setParameter(1, itemCategory);
       nativeQuery.setParameter(2, itemCategory2);
       nativeQuery.setParameter(3,todate);


       Double sumQty = (Double) nativeQuery.uniqueResult();
       return sumQty;

   }

    }

    @Override
    public boolean isExisits(String itemCategory, String todate) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM OrderDetail WHERE icategory=?1 AND todate=?2");
        nativeQuery.setParameter(1,itemCategory);
        nativeQuery.setParameter(2,todate);
        System.out.println(nativeQuery.list().size());
        if (nativeQuery.list().size()>0){
            return true;
        }else{
            return false;
        }



    }


}
