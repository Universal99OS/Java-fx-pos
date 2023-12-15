package model;

import dto.tablemodel.OrderViewTm;
import javafx.collections.ObservableList;

public interface OrderViewModel {
    ObservableList<OrderViewTm> allOrderViews();
}
