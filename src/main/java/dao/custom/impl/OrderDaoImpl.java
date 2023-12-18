package dao.custom.impl;

import Entity.Orders;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import dao.util.CrudUtil;
import db.DbConnector;
import dto.OrderdDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao{

    @Override
    public Orders lastOrder() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        ResultSet resultSet=CrudUtil.execute(sql);
        if(resultSet.next()){
            return new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES(?,?,?)";
        return CrudUtil.execute(sql,entity.getId(),entity.getDate(),entity.getCustomerId());
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM orders WHERE id=?";
        return CrudUtil.execute(sql,value);

    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orderEntities=new ArrayList<>();

        String sql="SELECT * FROM orders";
        ResultSet resultSet=CrudUtil.execute(sql);
        while (resultSet.next()){
            orderEntities.add(new Orders(
               resultSet.getString(1),
               resultSet.getString(2),
               resultSet.getString(3)
            ));
        }

        return orderEntities;
    }
}
