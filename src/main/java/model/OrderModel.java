package model;

import dto.OrderdDto;

import java.sql.SQLException;

public interface OrderModel {
    boolean saveOrder(OrderdDto dto) throws SQLException, ClassNotFoundException;
    OrderdDto lastOrder() throws SQLException, ClassNotFoundException;

}
