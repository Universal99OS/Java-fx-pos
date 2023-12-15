package model.impl;

import db.DbConnector;
import dto.OrderdDto;
import model.CustomerModel;
import model.OrderDetailsModel;
import model.OrderModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModelImpl implements OrderModel {

    OrderDetailsModel orderDetailsModel=new OrderDetailsModelImpl();

    @Override
    public boolean saveOrder(OrderdDto dto) throws SQLException {
        Connection connection =null;
        try {
            connection = DbConnector.getInstance().getConnection();
            connection.setAutoCommit(false);


            String sql = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, dto.getOrderId());
            pstm.setString(2, dto.getDate());
            pstm.setString(3, dto.getCustomerId());

            if (pstm.executeUpdate() > 0) {
                boolean isDetailsSaved = orderDetailsModel.saveOrderDtails(dto.getList());
                if (isDetailsSaved) {
                    connection.commit();
                    return true;
                }
            }
        }catch (SQLException | ClassNotFoundException ex){
            connection.rollback();
            ex.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;

    }

    @Override
    public OrderdDto lastOrder() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        PreparedStatement pstm =DbConnector.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return new OrderdDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }
}
