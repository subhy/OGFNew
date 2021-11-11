package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.dto.PackDTO;

import java.util.List;

public interface PackBO extends SuperBO {

    void savePack(PackDTO packDTO) throws Exception;

    void updatePack(PackDTO pack) throws Exception;

    void deletePack(String icode) throws Exception;

    List<PackDTO> findAllItems() throws Exception;

    PackDTO findItem(String iCode) throws Exception;

    List<PackDTO> findSearchItems(String val) throws Exception;

    List<String> getAllCodes(String text) throws Exception;
}
