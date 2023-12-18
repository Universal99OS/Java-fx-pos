package dao.custom.impl;

import Entity.Item;
import dao.custom.ItemDao;
import dao.util.CrudUtil;
import db.DbConnector;
import dto.ItemDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    @Override
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE code=?";

        ResultSet resultSet=CrudUtil.execute(sql,code);
         if(resultSet.next()){
             return new Item(
                     resultSet.getString(1),
                     resultSet.getString(2),
                     resultSet.getDouble(3),
                     resultSet.getInt(4)
             );
         }else {
             return null;
         }


    }


    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO item VALUES(?,?,?,?)";
        return CrudUtil.execute(sql, entity.getCode(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET description=?, unitprice=?, qtyonhand=? WHERE code=?";
       return CrudUtil.execute(sql,entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand(),entity.getCode());
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE from item WHERE code=?";
        return CrudUtil.execute(sql,value);
    }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        List<Item> list=new ArrayList<>();
        String sql = "SELECT * FROM item";

        ResultSet resultSet=CrudUtil.execute(sql);
        while (resultSet.next()){
            list.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return list;
    }
}
