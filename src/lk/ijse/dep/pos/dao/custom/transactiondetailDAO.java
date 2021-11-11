package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.entity.transactiondetail;
import java.util.List;

public interface transactiondetailDAO extends CrudDAO<transactiondetail, String> {

    List<transactiondetail> findTransDetailByTransNo(String transNo);

    Integer getLastOrderId() throws Exception;

    boolean existsByCateDate(String oilSale, String toDate, String pMethod, String subCate) throws Exception;

    Double findTotal(String sDateFrom, String sDateTo, String pType, String subCate) throws Exception;

    String getTransNoByOrderId(String orderId) throws Exception;

    String findTransDetailByDateCate(String accNo, String dpDate, String accDesc) throws Exception;

    Double findTotalAndDisc(String oId, String oilSale, String toDate,String pMethod,String subCate) throws Exception;
}
