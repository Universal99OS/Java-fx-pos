package Controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrderdDto;
import dto.tablemodel.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import dao.CustomerModel;
import dao.ItemModel;
import dao.OrderModel;
import dao.CustomerModelImpl;
import dao.ItemModelImpl;
import dao.OrderModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {

    public JFXComboBox cusIdComboBox;
    public JFXComboBox orderIdComboBox;
    public JFXTextField nameField;
    public JFXTextField descriptionField;
    public JFXTextField priceField;
    public JFXTextField qtyField;
    public JFXButton addtocartbtn;
    public JFXTreeTableView<OrderTm> orderTable;
    public TreeTableColumn colCode;
    public TreeTableColumn colDes;
    public TreeTableColumn colQty;
    public TreeTableColumn colAmount;
    public TreeTableColumn colOption;
    public Text totalText;
    public AnchorPane orderPane;
    public Text orderIdText;

    private List<CustomerDto> customers;
    private List<ItemDto> items;

    private CustomerModel customerModel=new CustomerModelImpl();
    private ItemModel itemModel=new ItemModelImpl();

    private OrderModel orderModel=new OrderModelImpl();
    private double tot=0;

    private ObservableList<OrderTm> tmList=FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDes.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        generateId();

        loadCustomerIds();
        loadItemIds();

        cusIdComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, id) -> {
            for (CustomerDto dto:customers) {
                if(dto.getId().equals(id)){
                    nameField.setText(dto.getName());
                }
            }
        });

        orderIdComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, newvalue) ->{
            for (ItemDto dto:items){
                if(dto.getCode().equals(newvalue)){
                    descriptionField.setText(dto.getDescription());
                    priceField.setText(String.format("%.2f",dto.getUniPrice()));

                }
            }
        } );


    }

    private void loadItemIds() {
        items=itemModel.allItems();
        ObservableList<String> list= FXCollections.observableArrayList();
        for (ItemDto dto:items) {
            list.add(dto.getCode());
        }
        orderIdComboBox.setItems(list);


    }

    private void loadCustomerIds() {
        try {
            customers=customerModel.allCustomers();
            ObservableList<String> list=FXCollections.observableArrayList();
            for (CustomerDto dto:customers) {
                list.add(dto.getId());
            }
            cusIdComboBox.setItems( list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage)orderPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addtoCartBtnOnAction(ActionEvent actionEvent) {
        double amount=itemModel.getItem(orderIdComboBox.getValue().toString()).getUniPrice()*Double.parseDouble(qtyField.getText());
        JFXButton btn=new JFXButton("Delete");
        OrderTm tm=new OrderTm(
                orderIdComboBox.getValue().toString(),
                descriptionField.getText(),
                Integer.parseInt(qtyField.getText()),
                 amount,
                btn
        );

        btn.setOnAction(ActionEvent->{
            tmList.remove(tm);
            tot-=tm.getAmount();
            orderTable.refresh();
            totalText.setText(String.format("%.2f",tot));

        });
        boolean isExist=false;

        for (OrderTm order:tmList) {
            if(order.getCode().equals(tm.getCode())){
                order.setQty(order.getQty()+ tm.getQty());
                order.setAmount(order.getAmount()+ tm.getAmount());
                isExist=true;
                tot+=tm.getAmount();

            }
        }

        if(!isExist){
            tmList.add(tm);
            tot+=tm.getAmount();
        }

        TreeItem<OrderTm> TreeObject = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        orderTable.setRoot(TreeObject);
        orderTable.setShowRoot(false);

        totalText.setText(String.format("%.2f",tot));
    }
    private void generateId() throws SQLException, ClassNotFoundException {
        OrderdDto orderdDto = orderModel.lastOrder();

        if(orderdDto!=null){
            String orderId = orderdDto.getOrderId();
            int num = Integer.parseInt(orderId.split("[D]")[1]);
            num++;
            orderIdText.setText(String.format("D%03d",num));
        }else{
            orderIdText.setText("D001");
        }

    }
    public void placeOrderBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        List<OrderDetailsDto> list=new ArrayList<>();
        for (OrderTm dto:tmList) {
            list.add(new OrderDetailsDto(
                orderIdText.getText(),
                dto.getCode(),
                dto.getQty(),
                    dto.getAmount()/dto.getQty()

            ));
        }
        if(!tmList.isEmpty()) {
           boolean isSaved=orderModel.saveOrder(new OrderdDto(
                   orderIdText.getText(),
                   LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")).toString(),
                   cusIdComboBox.getValue().toString(),
                   list
           ));

           if(isSaved){
               new Alert(Alert.AlertType.INFORMATION,"Order Saved").show();
           }else {
               new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
           }
        }

    }
}
