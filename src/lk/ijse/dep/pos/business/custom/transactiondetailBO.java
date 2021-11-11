package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.dto.transactiondetailDTO;

import java.util.List;

public interface transactiondetailBO extends SuperBO {

    String findTransDetailByDateCategory(String accNo, String valueOf, String accDes) throws Exception;

    List<transactiondetailDTO> findTransDetail(String transNo) throws Exception;

    void updateTransDetail(transactiondetailDTO transDetail) throws Exception;

    boolean existsByCateDate(String oil_sale, String onDate, String income, String oil_sale1) throws Exception;

    Double getTotal(String onDate, String onDate1, String income, String oil_sale) throws Exception;

    Integer getLastOrderId() throws Exception;

    void saveTransDetail(transactiondetailDTO transDetailDTO) throws Exception;

    String getTransaNoByOrderId(String text) throws Exception;

    void deleteTransDetail(String transNo) throws Exception;

    Double findTotalAndDisc(String oId, String oilSale, String toDate,String pMethod,String subCate) throws Exception;
}
