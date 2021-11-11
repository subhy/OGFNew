package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.entity.Item;

import java.util.List;

public interface ItemDAO extends CrudDAO<Item, String> {

    String getLastItemCode() throws Exception;

    List<Item> getSearchList(String value) throws Exception;

    double findMinQty(String pack, String pack1, String pack2, String pack3, String pack4) throws Exception;

    String findItemByDes(String iDesc) throws Exception;
}
