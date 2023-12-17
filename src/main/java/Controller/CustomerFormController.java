package Controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.impl.CustomerBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import db.DbConnector;
import dto.CustomerDto;
import dto.tablemodel.CustomerTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dao.custom.CustomerDao;
import dao.custom.impl.CustomerDaoImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class CustomerFormController {

    public AnchorPane customerPane;
    @FXML
    private JFXTextField idField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField addressField;

    @FXML
    private JFXTextField salaryField;

    @FXML
    private TableView<CustomerTableModel> tblCustomer;

    @FXML
    private TableColumn collId;

    @FXML
    private TableColumn collName;

    @FXML
    private TableColumn collAddress;

    @FXML
    private TableColumn collSalary;

    @FXML
    private TableColumn collOption;

    CustomerBo customerBo=BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize(){

        collId.setCellValueFactory(new PropertyValueFactory<>("id"));
        collName.setCellValueFactory(new PropertyValueFactory<>("name"));
        collAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        collSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        collOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, newValue) ->{
            setData(newValue);
        } );


    }

    private void setData(CustomerTableModel newValue) {
        tblCustomer.refresh();
        idField.setEditable(false);
        idField.setText(newValue.getId());
        nameField.setText(newValue.getName());
        addressField.setText(newValue.getAddress());
        salaryField.setText(String.valueOf(newValue.getSalary()));
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTableModel> tmList = FXCollections.observableArrayList();


        try {
            List<CustomerDto> list= customerBo.allCustomers();
            for(CustomerDto c:list) {
                JFXButton btn = new JFXButton("Delete");
                CustomerTableModel ctm=new CustomerTableModel(
                      c.getId(),
                      c.getName(),
                      c.getAddress(),
                      c.getSalary(),
                      btn
                );


                btn.setOnAction(actionEvent -> {
                            try {
                                boolean b = customerBo.deleteCustomer(ctm.getId());
                                if (b==true) {
                                    new Alert(Alert.AlertType.INFORMATION, "Item Deleted").show();
                                    loadCustomerTable();
                                } else {
                                    new Alert(Alert.AlertType.INFORMATION, "Item Not Delete").show();
                                }
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());;
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                );

                tmList.add(ctm);

            }
        }catch (Exception r){
            r.printStackTrace();
        }



        tblCustomer.setItems(tmList);

    }



    public void reloadButtonOnAction(javafx.event.ActionEvent actionEvent) {
        loadCustomerTable();
        tblCustomer.refresh();
        clearFields();

    }

    private void clearFields() {
        tblCustomer.refresh();
        idField.clear();
        nameField.clear();
        addressField.clear();
        salaryField.clear();
        idField.setEditable(true);

    }

    public void saveVuttonOnAction(ActionEvent actionEvent) {
        CustomerDto c=new CustomerDto(idField.getText(),
                nameField.getText(),
                addressField.getText(),
                Double.parseDouble(salaryField.getText())
        );


        boolean i = false;
        try {
            i = customerBo.saveCustomer(c);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (i==true) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Saved").show();
        }

        clearFields();
        loadCustomerTable();
    }


    public void updateButtonOnAction(ActionEvent actionEvent) {
        updateCustomerTable();
        loadCustomerTable();
    }

    private void updateCustomerTable() {
        CustomerDto dto=new CustomerDto(
                idField.getText(),
                nameField.getText(),
                addressField.getText(),
                Double.parseDouble(salaryField.getText())
        );

        boolean b = false;
        try {
            b = customerBo.updateCustomer(dto);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if(b==true){
            new Alert(Alert.AlertType.INFORMATION,"Succefully Updated").show();
        }else new Alert(Alert.AlertType.ERROR, "There have some thing went wrong").show();

    }


    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage)customerPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

