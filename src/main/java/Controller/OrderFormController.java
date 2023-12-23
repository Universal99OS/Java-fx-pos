package Controller;

import bo.BoFactory;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.OrderDetailsDto;
import dto.OrderdDto;
import dto.tablemodel.OrderDetailsViewTm;
import dto.tablemodel.OrderViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import dao.custom.ItemDao;
import dao.custom.OrderDetailsDao;
import dao.custom.impl.ItemDaoImpl;
import dao.custom.impl.OrderDetailsDaoImpl;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderFormController {
    public JFXTreeTableView<OrderViewTm> orderTableId;
    public TreeTableColumn colOrderId;
    public TreeTableColumn colDate;
    public TreeTableColumn colCusId;
    public TreeTableColumn colNameId;
    public TreeTableColumn colAmountId;
    public TreeTableColumn colOptionId;
    public JFXTreeTableView<OrderDetailsViewTm> orderDetailsTableId;
    public TreeTableColumn colItemId;
    public TreeTableColumn colDesId;
    public TreeTableColumn colUnitPriceId;
    public TreeTableColumn colQtyId;
    public TreeTableColumn colAmountItemId;
    public TreeTableColumn colOptionItemId;

    OrderBo orderBo= BoFactory.getInstance().getBo(BoType.ORDERS);



    public void initialize(){
        colOrderId.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colCusId.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerId"));
        colNameId.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colAmountId.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOptionId.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        colItemId.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemId"));
        colDesId.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colUnitPriceId.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQtyId.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmountItemId.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOptionItemId.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        loadOrderViewTable();
        orderTableId.setOnMouseClicked(event ->{
            TreeItem<OrderViewTm> selectedTreeItem=orderTableId.getSelectionModel().getSelectedItem();
            if(selectedTreeItem != null){
                OrderViewTm orderViewTm=selectedTreeItem.getValue();
                loadOrderDetailsViewTable(orderViewTm.getOrderId());
            }
        });


    }

    private void loadOrderDetailsViewTable(String orderId) {
        List<OrderDetailsViewTm> detailsView=orderBo.getAllOrderDetails(orderId);
        ObservableList<OrderDetailsViewTm> detailsViewTms=FXCollections.observableArrayList();

        for (OrderDetailsViewTm orderView:detailsView) {
            detailsViewTms.add(orderView);
        }

        TreeItem<OrderDetailsViewTm> treeItem=new RecursiveTreeItem<>(detailsViewTms,RecursiveTreeObject::getChildren);
        orderDetailsTableId.setRoot(treeItem);
        orderDetailsTableId.setShowRoot(false);
    }

    public void loadOrderViewTable() {

        try {
            List<OrderViewTm> tmList = orderBo.getAllOrderView(this);
            ObservableList<OrderViewTm> observableList=FXCollections.observableArrayList();
            for (OrderViewTm orderViewTm:tmList) {
                observableList.add(orderViewTm);
            }
            TreeItem<OrderViewTm> treeItem=new RecursiveTreeItem<>(observableList, RecursiveTreeObject::getChildren);
            orderTableId.setRoot(treeItem);
            orderTableId.setShowRoot(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }


    public void backBtnOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage)orderTableId.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
