package dao.custom;

import Entity.OrderDetail;
import dao.CrudDao;
import dto.OrderDetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDetailsDao extends CrudDao<OrderDetail> {

    boolean isDelete(String orderId, String itemCode) throws SQLException, ClassNotFoundException;

    List<OrderDetail> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;
}
