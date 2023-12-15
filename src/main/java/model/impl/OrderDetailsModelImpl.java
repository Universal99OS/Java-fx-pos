package model.impl;

import db.DbConnector;
import dto.OrderDetailsDto;
import model.OrderDetailsModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsModelImpl implements OrderDetailsModel {

    @Override
    public boolean saveOrderDtails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isOrderDetailsSaved=true;
        for (OrderDetailsDto dto:list) {
            String sql="INSERT INTO orderdetail VALUES(?,?,?,?)";
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQty());
            pstm.setDouble(4,dto.getPrice());

            if(!(pstm.executeUpdate()>0)){
                isOrderDetailsSaved=false;
            }
        }
        return isOrderDetailsSaved;

    }

    @Override
    public double orderAmount(String orderId) {
        double amount=0.0d;
        String sql="Select * FROM orderdetails WHERE orderid=?";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,orderId);
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                amount+=(resultSet.getInt(3)*resultSet.getDouble(4));
            }
            return amount;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return amount;

    }

    @Override
    public ArrayList<OrderDetailsDto> getAll(String orderId) {
        ArrayList<OrderDetailsDto> orderDetailsDtos=new ArrayList<>();
        String sql="SELECT * FROM orderdetail WHERE orderid=?";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,orderId);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                orderDetailsDtos.add(new OrderDetailsDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4)
                ));
            }
            return orderDetailsDtos;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
