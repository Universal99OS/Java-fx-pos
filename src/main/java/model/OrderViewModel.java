package model;

import Controller.OrderFormController;
import dto.tablemodel.OrderViewTm;
import javafx.collections.ObservableList;

public interface OrderViewModel {
    ObservableList<OrderViewTm> allOrderViews(OrderFormController b);
}
