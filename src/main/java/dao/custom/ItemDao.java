package dao.custom;

import Entity.Item;
import dao.CrudDao;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao extends CrudDao<Item> {

    Item getItem(String code) throws SQLException, ClassNotFoundException;

}
