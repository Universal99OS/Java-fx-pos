package bo.custom.impl;

import Entity.Item;
import bo.custom.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.util.DaoType;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    ItemDao itemDao=DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
       return itemDao.save(new Item(
             dto.getCode(),
             dto.getDescription(),
             dto.getUniPrice(),
             dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getUniPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean deleteItem(String value) throws SQLException, ClassNotFoundException {
        return itemDao.delete(value);
    }

    @Override
    public List<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        List<Item> entityItems=itemDao.getAll();
        List<ItemDto> dtos=new ArrayList<>();
        for (Item item:entityItems) {
            dtos.add(new ItemDto(
                    item.getCode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            ));
        }
        return dtos;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDao.getItem(code);
        return new ItemDto(
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        );
    }

    @Override
    public String getDescription(String code) throws SQLException, ClassNotFoundException {
        return itemDao.getItem(code).getDescription();
    }
}
