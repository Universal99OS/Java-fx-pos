//package dao.custom.impl;
//
//import Controller.OrderFormController;
//import com.jfoenix.controls.JFXButton;
//import dao.custom.CustomerDao;
//import dao.custom.OrderDetailsDao;
//import dao.custom.OrderDao;
//import dao.custom.OrderViewDao;
//import dto.OrderdDto;
//import dto.tablemodel.OrderViewTm;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.Alert;
//
//import java.util.ArrayList;
//
//public class OrderViewDaoImpl implements OrderViewDao {
//    OrderDao orderModel=new dao.custom.impl.OrderDaoImpl();
//    CustomerDao customerDao =new dao.custom.impl.CustomerDaoImpl();
//    OrderDetailsDao orderDetailsDao =new dao.custom.impl.OrderDetailsDaoImpl();
//    @Override
//    public ObservableList<OrderViewTm> allOrderViews(OrderFormController b) {
//        ObservableList<OrderViewTm> tmList= FXCollections.observableArrayList();
//        ArrayList<OrderdDto> orderList=orderModel.allOrders();
//
//        for (OrderdDto order: orderList) {
//            JFXButton button=new JFXButton("Delete");
//            tmList.add(new OrderViewTm(
//                    order.getOrderId(),
//                    order.getDate(),
//                    order.getCustomerId(),
//                    customerDao.getCustomerName(order.getCustomerId()),
//                    orderDetailsDao.orderAmount(order.getOrderId()),
//                    button
//            ));
//
//            button.setOnAction(event->{
//                boolean delte = orderModel.isDelte(order.getOrderId());
//                if(delte){
//                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
//                }else{
//                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
//                }
//                b.loadOrderViewTable();
//                b.orderDetailsTableId.getRoot().getChildren().clear();
//
//            });
//        }
//
//        return tmList;
//    }
//}
