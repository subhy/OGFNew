package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailBO extends SuperBO {


    Double findAllItemQtyByCategory(String itemCategory,String itemCategory2,String todate) throws Exception;

    boolean isExisitsOrder(String itemCategory, String todate) throws Exception;


}
