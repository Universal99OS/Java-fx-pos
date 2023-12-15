package model.impl;

import Controller.OrderFormController;
import com.jfoenix.controls.JFXButton;
import db.DbConnector;
import dto.OrderdDto;
import dto.tablemodel.OrderViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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
    public ObservableList<OrderViewTm> allOrderViews(OrderFormController b) {
        ObservableList<OrderViewTm> tmList= FXCollections.observableArrayList();
        ArrayList<OrderdDto> orderList=orderModel.allOrders();

        for (OrderdDto order: orderList) {
            JFXButton button=new JFXButton("Delete");
            tmList.add(new OrderViewTm(
                    order.getOrderId(),
                    order.getDate(),
                    order.getCustomerId(),
                    customerModel.getCustomerName(order.getCustomerId()),
                    orderDetailsModel.orderAmount(order.getOrderId()),
                    button
            ));

            button.setOnAction(event->{
                boolean delte = orderModel.isDelte(order.getOrderId());
                if(delte){
                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                }
                b.loadOrderViewTable();
                b.orderDetailsTableId.getRoot().getChildren().clear();

            });
        }

        return tmList;
    }
}
