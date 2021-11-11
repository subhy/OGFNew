package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.dto.PurchaseDTO;
import lk.ijse.dep.pos.dto.PurchaseOrderDTO;

import java.util.List;

public interface PurchaseOrderBO extends SuperBO {

    void savePDetail(PurchaseOrderDTO purchaseOrderDTO) throws Exception;

    void updatePDetail() throws  Exception;

    void deletePDetail() throws Exception;

    List<PurchaseOrderDTO> findAllPDetail() throws Exception;

    PurchaseDTO findPOrder(String pOrderId) throws Exception;

    Integer getLastOrderId()throws Exception;
}
