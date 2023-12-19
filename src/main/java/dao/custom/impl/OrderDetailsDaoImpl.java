package dao.custom.impl;

import Entity.OrderDetail;
import dao.custom.OrderDetailsDao;
import dao.util.CrudUtil;
import db.DbConnector;
import dto.OrderDetailsDto;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {




    @Override
    public boolean isDelete(String orderId, String itemCode) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM orderdetail WHERE orderid=? AND itemcode=?";
        return CrudUtil.execute(sql,orderId,itemCode);

    }

    @Override
    public List<OrderDetail> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        String sql="select * FROM orderdetail WHERE orderid=?";
        ResultSet resultSet=CrudUtil.execute(sql,orderId);
        List<OrderDetail> entities=new ArrayList<>();
        while (resultSet.next()){
            entities.add(new OrderDetail(
               resultSet.getString(1),
               resultSet.getString(2),
               resultSet.getInt(3),
               resultSet.getDouble(4)
            ));
        }
        return entities;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO orderdetail VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,entity.getOrderId(),entity.getItemCode(),entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM orderdetail";
        ResultSet resultSet=CrudUtil.execute(sql);
        List<OrderDetail> entitys=new ArrayList<>();

        while (resultSet.next()){
            entitys.add(new OrderDetail(
                 resultSet.getString(1),
                 resultSet.getString(2),
                 resultSet.getInt(3),
                 resultSet.getDouble(4)
            ));
        }
        return entitys;
    }
}
