package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.ItemDaoImpl;
import dto.ItemDto;
import dto.tablemodel.ItemTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;

import java.io.IOException;
import java.util.List;


public class ItemFormController {
    public JFXTextField itemIDField;
    public JFXTextField descriptionField;
    public JFXTextField priceField;
    public JFXTextField qtyOnHandField;
    public TableView<ItemTableModel> itemTable;
    public TableColumn collItemId;
    public TableColumn collPrice;
    public TableColumn collQtyOnHand;
    public TableColumn collOption;
    public TableColumn collDescription;
    public AnchorPane itemPane;
    public JFXTextField searchText;

    private ItemDao itemDao =new ItemDaoImpl();

    public void initialize(){
        collItemId.setCellValueFactory(new PropertyValueFactory<>("code"));
        collDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        collPrice.setCellValueFactory(new PropertyValueFactory<>("uniPrice"));
        collQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        collOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadItemTable();


        itemTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, newValue) ->{
            setData(newValue);
        } );

    }

    private void setData(ItemTableModel newValue) {
        itemIDField.setEditable(false);
        itemIDField.setText(newValue.getCode());
        descriptionField.setText(newValue.getDescription());
        priceField.setText(String.valueOf(newValue.getUniPrice()));
        qtyOnHandField.setText(String.valueOf(newValue.getQtyOnHand()));
    }


    private void loadItemTable() {
        itemTable.refresh();
        ObservableList<ItemTableModel> itemList= FXCollections.observableArrayList();


        try{
            List<ItemDto> list= itemDao.allItems();
        for(ItemDto dto:list){
            JFXButton btn=new JFXButton("Delete");
            ItemTableModel itm=new ItemTableModel(
                    dto.getCode(),
                    dto.getDescription(),
                    dto.getUniPrice(),
                    dto.getQtyOnHand(),
                    btn);
            itemList.add(itm);
            btn.setOnAction(ActionEvent->{
                itemDao.deleteItem(itm.getCode());
            });
        }
        itemTable.setItems(itemList);
        }catch(Exception er){
            er.printStackTrace();
        }

    }


    public void saveButtonOnAction(ActionEvent actionEvent) {
        ItemDto item=new ItemDto(itemIDField.getText(),
                descriptionField.getText(),
                Double.parseDouble(priceField.getText()),
                Integer.parseInt(qtyOnHandField.getText()));


        boolean i= itemDao.saveItem(item);
        if(i==true) {
            new Alert(Alert.AlertType.INFORMATION, "Succefully Saved").show();
            loadItemTable();
        } else new Alert(Alert.AlertType.ERROR,"Not Saved").show();
    }

    public void updateButoonOnAction(ActionEvent actionEvent) {
        updateItemTable();
        loadItemTable();
    }

    private void updateItemTable() {
        ItemDto item=new ItemDto(itemIDField.getText(),
                descriptionField.getText(),
                Double.parseDouble(priceField.getText()),
                Integer.parseInt(qtyOnHandField.getText()));

        boolean i = itemDao.updateItem(item) ;
        if(i==true) {
            new Alert(Alert.AlertType.INFORMATION, "Succefully Updated").show();

        } else new Alert(Alert.AlertType.ERROR,"Not Saved").show();
    }

    public void reloadButtonOnAction(ActionEvent actionEvent) {
        clearItemFields();
        loadItemTable();

    }

    private void clearItemFields() {
        itemIDField.setEditable(true);
        itemIDField.clear();
        descriptionField.clear();
        priceField.clear();
        qtyOnHandField.clear();


    }


    public void backBtnOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage)itemPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
