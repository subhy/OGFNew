package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.dto.OrderListDTO;

public interface OrderListBO extends SuperBO {

    void saveItem(OrderListDTO orderItem) throws Exception;

    int getLastOrderId() throws Exception;




    /*void updateItem(ItemDTO item) throws Exception;

    void deleteItem(String itemCode) throws Exception;

    List<ItemDTO> findAllItems() throws Exception;

    ItemDTO findItem(String itemCode) throws Exception;

    List<ItemDTO> findSearchItems(String value) throws Exception;

    List<String> getAllItemCodes() throws Exception;

    String getLastItemCode() throws Exception;

    double findMinQty(String pack, String pack1, String pack2, String pack3, String pack4) throws Exception;

    String findItemBydes(String iDesc) throws Exception;*/
}
