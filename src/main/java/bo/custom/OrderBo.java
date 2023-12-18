package bo.custom;

import bo.SuperBo;
import dto.OrderDetailsDto;
import dto.OrderdDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {
    boolean saveOrder(OrderdDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteOrder(String value) throws SQLException, ClassNotFoundException;
    List<OrderdDto> getAll() throws SQLException, ClassNotFoundException;

    String getNewId() throws SQLException, ClassNotFoundException;
}
