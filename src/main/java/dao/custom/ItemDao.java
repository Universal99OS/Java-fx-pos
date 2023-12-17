package dao.custom;

import dto.ItemDto;

import java.util.List;

public interface ItemDao {
    boolean saveItem(ItemDto dto);
    boolean updateItem(ItemDto dto);

    boolean deleteItem(String code);
    ItemDto getItem(String code);

    List allItems();

    String getItemDes(String code);
}
