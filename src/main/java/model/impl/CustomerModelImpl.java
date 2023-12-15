package model.impl;

import db.DbConnector;
import dto.CustomerDto;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO customer VALUES(?,?,?,?)";
        PreparedStatement pstm= DbConnector.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getAddress());
        pstm.setDouble(4,dto.getSalary());

        return pstm.executeUpdate()>0;

    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        String sql="Update customer SET name=?, address=?, salary=? WHERE id=?";
        PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setDouble(3,dto.getSalary());
        pstm.setString(4, dto.getId());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM customer WHERE id=?";
        PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,id);

        return pstm.executeUpdate()>0;


    }

    @Override
    public List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException {

        String sql="SELECT * FROM customer";
        PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        List<CustomerDto> list= new ArrayList<>();

        while (rst.next()){
            list.add(
                    new CustomerDto(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4)
                    )
            );
        }
        return list;
    }

    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }

    @Override
    public String getCustomerName(String customerId) {
        String sql="SELECT * FROM customer WHERE id=?";

        try {
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,customerId);
            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();
            return resultSet.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
