package Controller;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.tablemodel.OrderViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.OrderViewModel;
import model.impl.OrderViewModelImpl;

public class OrderFormController {
    public JFXTreeTableView<OrderViewTm> orderTableId;
    public TreeTableColumn colOrderId;
    public TreeTableColumn colDate;
    public TreeTableColumn colCusId;
    public TreeTableColumn colNameId;
    public TreeTableColumn colAmountId;
    public TreeTableColumn colOptionId;
    public JFXTreeTableView orderDetailsTableId;
    public TreeTableColumn colItemId;
    public TreeTableColumn colDesId;
    public TreeTableColumn colUnitPriceId;
    public TreeTableColumn colQtyId;
    public TreeTableColumn colAmountItemId;
    public TreeTableColumn colOptionItemId;

    OrderViewModel orderViewModel=new OrderViewModelImpl();

    public void initialize(){
        colOrderId.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colCusId.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerId"));
        colNameId.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colAmountId.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOptionId.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        loadOrderViewTable();

    }

    private void loadOrderViewTable() {
        ObservableList<OrderViewTm> tmList= orderViewModel.allOrderViews();

        TreeItem<OrderViewTm> treeItem=new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        orderTableId.setRoot(treeItem);
        orderTableId.setShowRoot(false);

    }


}
