package dao.custom;

import Entity.Orders;
import dao.CrudDao;
import dao.SuperDao;
import dto.OrderdDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDao extends CrudDao<Orders> {

    Orders lastOrder() throws SQLException, ClassNotFoundException;


}
