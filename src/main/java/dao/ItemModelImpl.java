package dao;

import db.DbConnector;
import dto.ItemDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemDto dto) {
        String sql="INSERT INTO item VALUES(?,?,?,?)";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getCode());
            pstm.setString(2,dto.getDescription());
            pstm.setDouble(3,dto.getUniPrice());
            pstm.setInt(4,dto.getQtyOnHand());

            return pstm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public boolean updateItem(ItemDto dto) {
        String sql = "UPDATE item SET description=?, unitprice=?, qtyonhand=? WHERE code=?";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getDescription());
            pstm.setDouble(2,dto.getUniPrice());
            pstm.setInt(3,dto.getQtyOnHand());
            pstm.setString(4,dto.getCode());

            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteItem(String code) {
        String sql = "DELETE from item WHERE code=?";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,code);
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ItemDto getItem(String code) {
        String sql = "SELECT * FROM item WHERE code=?";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,code);
            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                return new ItemDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List allItems() {
        List<ItemDto> list=new ArrayList<>();
        String sql = "SELECT * FROM item";
        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                list.add(new ItemDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                ));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String getItemDes(String code) {
        ItemDto item = getItem(code);
        return item.getDescription();
    }
}
