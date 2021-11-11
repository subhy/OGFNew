package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import lk.ijse.dep.pos.entity.ODetails;
import org.hibernate.Session;

import java.sql.Date;
import java.util.List;

public interface ODetailDAO extends CrudDAO<ODetails,Integer> {

    int getLastOrderId() throws Exception;

    Double findByPMethod(String oId, String pMethod, String dateTo, String dateFrom, String s2) throws Exception;

    List<CustomEntity> getOrdersInfo3(Date dateFrom, Date dateTo) throws Exception;
}
