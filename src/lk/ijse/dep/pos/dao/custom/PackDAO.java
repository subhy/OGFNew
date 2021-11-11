package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.dto.PackDTO;
import lk.ijse.dep.pos.entity.Pack;

import java.util.List;


public interface PackDAO extends CrudDAO<Pack, String> {

    List<String> findAllPackNo(String code) throws Exception;

    void updateQty(double qty, String code) throws Exception;

    List<PackDTO> findSearchItem(String val) throws Exception;

}
