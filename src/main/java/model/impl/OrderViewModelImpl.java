package model.impl;

import db.DbConnector;
import dto.OrderdDto;
import dto.tablemodel.OrderViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CustomerModel;
import model.OrderDetailsModel;
import model.OrderModel;
import model.OrderViewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderViewModelImpl implements OrderViewModel {
    OrderModel orderModel=new OrderModelImpl();
    CustomerModel customerModel=new CustomerModelImpl();
    OrderDetailsModel orderDetailsModel=new OrderDetailsModelImpl();
    @Override
    public ObservableList<OrderViewTm> allOrderViews() {
        ObservableList<OrderViewTm> tmList= FXCollections.observableArrayList();
        ArrayList<OrderdDto> orderList=orderModel.allOrders();

        for (OrderdDto order: orderList) {
            tmList.add(new OrderViewTm(
                    order.getOrderId(),
                    order.getDate(),
                    order.getCustomerId(),
                    customerModel.getCustomerName(order.getCustomerId()),
                    orderDetailsModel.orderAmount(order.getOrderId()),
                    null
            ));
        }

        return tmList;
    }
}
