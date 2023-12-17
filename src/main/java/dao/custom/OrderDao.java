package dao.custom;

import dto.OrderdDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDao {
    boolean saveOrder(OrderdDto dto) throws SQLException, ClassNotFoundException;
    OrderdDto lastOrder() throws SQLException, ClassNotFoundException;

    ArrayList<OrderdDto> allOrders();

    boolean isDelte(String id);

}
