package model;

import dto.OrderdDto;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderModel {
    boolean saveOrder(OrderdDto dto) throws SQLException, ClassNotFoundException;
    OrderdDto lastOrder() throws SQLException, ClassNotFoundException;

    ArrayList<OrderdDto> allOrders();

    boolean isDelte(String id);

}
