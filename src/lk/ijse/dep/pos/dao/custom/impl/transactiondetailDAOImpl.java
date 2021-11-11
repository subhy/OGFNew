package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.TransDetailDAO;
import lk.ijse.dep.pos.dao.custom.transactiondetailDAO;
import lk.ijse.dep.pos.entity.TransDetail;
import lk.ijse.dep.pos.entity.transactiondetail;
import org.hibernate.query.NativeQuery;

import java.math.BigInteger;
import java.util.List;

public class transactiondetailDAOImpl extends CrudDAOImpl<transactiondetail,String> implements transactiondetailDAO {
    @Override
    public List<transactiondetail> findTransDetailByTransNo(String transNo) {
        return null;
    }

    @Override
    public Integer getLastOrderId() throws Exception {

        BigInteger laNo = (BigInteger) session.createNativeQuery("SELECT MAX(CAST(SUBSTRING(transNo,3) AS SIGNED)) FROM transactiondetail ORDER BY transNo DESC LIMIT 1").uniqueResult();
        int lastNo = laNo.intValue();
        return lastNo;
    }

    @Override
    public boolean existsByCateDate(String oilSale, String toDate, String pMethod, String subCate) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM transactiondetail WHERE transCate=?1 AND transDate=?2 AND transType=?3 AND transSubCate=?4");
        nativeQuery.setParameter(1, oilSale);
        nativeQuery.setParameter(2, toDate);
        nativeQuery.setParameter(3, pMethod);
        nativeQuery.setParameter(4, subCate);
        List resultList = nativeQuery.getResultList();


        return resultList.size() >= 1;

    }

    @Override
    public Double findTotal(String sDateFrom, String sDateTo, String pType, String subCate) throws Exception {
        Double transAmount = 0.00;
        NativeQuery nativeQuery = session.createNativeQuery("SELECT (transAmount) FROM transactiondetail WHERE transDate BETWEEN ?1 AND ?2 AND transType=?3 AND transSubCate=?4");
        nativeQuery.setParameter(1, sDateFrom);
        nativeQuery.setParameter(2, sDateTo);
        nativeQuery.setParameter(3, pType);
        nativeQuery.setParameter(4, subCate);


        transAmount = (Double) nativeQuery.uniqueResult();
        return transAmount;
    }

    @Override
    public String getTransNoByOrderId(String orderId) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT td.transNo FROM transactiondetail td WHERE td.transUserId=?1");
        nativeQuery.setParameter(1,orderId);
        String transNo = (String) nativeQuery.uniqueResult();
        return transNo;
    }

    @Override
    public String findTransDetailByDateCate(String accNo, String dpDate, String accDesc) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT td.transNo FROM transactiondetail td WHERE td.transAccNo=?1 AND td.transDate=?2 AND td.transDesc=?3");
        nativeQuery.setParameter(1,accNo);
        nativeQuery.setParameter(2,dpDate);
        nativeQuery.setParameter(3,accDesc);
        String transNo = (String) nativeQuery.uniqueResult();
        return transNo;

    }

    @Override
    public Double findTotalAndDisc(String oId, String oilSale, String toDate,String pMethod,String subCate) throws Exception {


       /* Double transAmount = 0.00;
        if (oilSale == "No Data" && oId=="") {
          mama methana Chuttak enas kala. Comment out kaloth eka balala hadanna


            NativeQuery nativeQuery = session.createNativeQuery("SELECT SUM(transAmount) FROM transactiondetail WHERE transAccNo=?1 AND transDate=?2");
            nativeQuery.setParameter(1, oilSale);
            nativeQuery.setParameter(2, toDate);
            nativeQuery.setParameter(3, pMethod);
            nativeQuery.setParameter(4, subCate);


            transAmount = (Double) nativeQuery.uniqueResult();


        } else if (oilSale == "Discount Given" && oId=="") {
            NativeQuery nativeQuery = session.createNativeQuery("SELECT transAmount FROM transactiondetail WHERE transSubCate=?1 AND transDate=?2 ");
            nativeQuery.setParameter(1, "Dis Oil Sale");
            nativeQuery.setParameter(2, toDate);



            transAmount = (Double) nativeQuery.uniqueResult();

        }else if (oilSale == "Discount Given" && oId=="On"){
            NativeQuery nativeQuery = session.createNativeQuery("SELECT transNo FROM TransDetail WHERE transSubCate=?1 AND transDate=?2");
            nativeQuery.setParameter(1, oilSale);
            nativeQuery.setParameter(2, toDate);

            System.out.println("Fu");
            String transAmount1 = String.valueOf(nativeQuery.uniqueResult());
            if (transAmount1 == null) {
                transAmount = 0.00;
            } else {

                transAmount = Double.parseDouble(transAmount1.replace("T", ""));
            }
        }else if (oilSale == "Oil Sale" && oId == "On") {

            NativeQuery nativeQuery = session.createNativeQuery("SELECT transNo FROM transactiondetail WHERE transCate=?1 AND transDate=?2 AND transType=?3 AND transSubCate=?4");
            nativeQuery.setParameter(1, oilSale);
            nativeQuery.setParameter(2, toDate);
            nativeQuery.setParameter(3, pMethod);
            nativeQuery.setParameter(4, subCate);

            String transAmount1 = String.valueOf(nativeQuery.uniqueResult());
            if (transAmount1 == null) {
                transAmount = 0.00;
            } else {

                transAmount = Double.parseDouble(transAmount1.replace("T", ""));
            }
        }else if(oilSale=="Credit"){


                NativeQuery nativeQuery = session.createNativeQuery("SELECT transAmount FROM transactiondetail WHERE transNo=?1");
                nativeQuery.setParameter(1, oId);


                transAmount = (Double) nativeQuery.uniqueResult();

            }
        return transAmount;
    }*/
        Double transAmount = 0.00;

        NativeQuery nativeQuery = session.createNativeQuery("SELECT SUM(transAmount) FROM transactiondetail WHERE transAccNo=?1 AND transDate=?2");
        nativeQuery.setParameter(1, oilSale);
        nativeQuery.setParameter(2, toDate);

        transAmount = (Double) nativeQuery.uniqueResult();

      return transAmount;
    }


}
