package dao.custom;

import Controller.OrderFormController;
import dto.tablemodel.OrderViewTm;
import javafx.collections.ObservableList;

public interface OrderViewDao {
    ObservableList<OrderViewTm> allOrderViews(OrderFormController b);
}
