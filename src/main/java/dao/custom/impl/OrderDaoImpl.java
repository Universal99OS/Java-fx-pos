package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDaoImpl;
import db.DbConnector;
import dto.OrderdDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {

    OrderDetailsDao orderDetailsDao =new OrderDetailsDaoImpl();

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
                boolean isDetailsSaved = orderDetailsDao.saveOrderDtails(dto.getList());
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

    @Override
    public ArrayList<OrderdDto> allOrders() {
        ArrayList<OrderdDto> orderList=new ArrayList<>();

        String sql="SELECT * FROM orders";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                orderList.add(new OrderdDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        null
                ));
            }
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean isDelte(String id) {
        String sql="DELETE FROM orders WHERE id=?";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,id);
            return pstm.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


}
