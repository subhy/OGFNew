package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.dto.ODetailDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;

import java.sql.Date;
import java.util.List;

public interface ODetailBO extends SuperBO {
    int getLastOrderId() throws Exception;

    void saveOrder(ODetailDTO detailDTO) throws Exception;

    void updateOrder(ODetailDTO detailDTO) throws Exception;

    void deleteOrder(int oNo) throws Exception;

    List<ODetailDTO> findAllItems() throws Exception;

    ODetailDTO findOrder(int oNo) throws Exception;

    List<OrderDTO2> getOrderInfo3(Date dateFrom, Date dateTo) throws Exception;

    Double findByPMethod(String oId, String pMethod, String dateTo, String dateFrom, String s2);
}
