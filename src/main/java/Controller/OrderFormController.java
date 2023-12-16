package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.OrderDetailsDto;
import dto.tablemodel.OrderDetailsViewTm;
import dto.tablemodel.OrderViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import dao.ItemModel;
import dao.OrderDetailsModel;
import dao.OrderViewModel;
import dao.ItemModelImpl;
import dao.OrderDetailsModelImpl;
import dao.OrderViewModelImpl;

import java.util.ArrayList;

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

    OrderViewModel orderViewModel=new OrderViewModelImpl();
    OrderDetailsModel orderDetailsModel=new OrderDetailsModelImpl();

    ItemModel itemModel=new ItemModelImpl();

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
        ArrayList<OrderDetailsDto> detailsDtos=orderDetailsModel.getAll(orderId);
        ObservableList<OrderDetailsViewTm> detailsViewTms=FXCollections.observableArrayList();

        for (OrderDetailsDto orderD: detailsDtos) {
            JFXButton btn=new JFXButton("Delete");
            OrderDetailsViewTm detailsViewTm=new OrderDetailsViewTm(
                    orderD.getItemCode(),
                    itemModel.getItemDes(orderD.getItemCode()),
                    orderD.getPrice(),
                    orderD.getQty(),
                    (orderD.getPrice()*orderD.getQty()),
                    btn

            );

            btn.setOnAction(event ->{
                orderDetailsModel.isDelete(orderD.getOrderId(),orderD.getItemCode());
                loadOrderDetailsViewTable(orderId);
            });
            detailsViewTms.add(detailsViewTm);

        }

        TreeItem<OrderDetailsViewTm> treeItem=new RecursiveTreeItem<>(detailsViewTms,RecursiveTreeObject::getChildren);
        orderDetailsTableId.setRoot(treeItem);
        orderDetailsTableId.setShowRoot(false);
    }

    public void loadOrderViewTable() {
        ObservableList<OrderViewTm> tmList= orderViewModel.allOrderViews(this);

        TreeItem<OrderViewTm> treeItem=new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        orderTableId.setRoot(treeItem);
        orderTableId.setShowRoot(false);

    }




}
