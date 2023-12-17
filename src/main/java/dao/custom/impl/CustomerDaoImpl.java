package dao.custom.impl;

import Entity.Customer;
import dao.custom.CustomerDao;
import db.DbConnector;
import dto.CustomerDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


    @Override
    public Customer searchCustomer(String id) {
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

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO customer VALUES(?,?,?,?)";
        PreparedStatement pstm= DbConnector.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,entity.getId());
        pstm.setString(2,entity.getName());
        pstm.setString(3,entity.getAddress());
        pstm.setDouble(4,entity.getSalary());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        String sql="Update customer SET name=?, address=?, salary=? WHERE id=?";
        PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,entity.getName());
        pstm.setString(2, entity.getAddress());
        pstm.setDouble(3,entity.getSalary());
        pstm.setString(4, entity.getId());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM customer WHERE id=?";
        PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,value);

        return pstm.executeUpdate()>0;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM customer";
        PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        List<Customer> list= new ArrayList<>();

        while (rst.next()){
            list.add(
                    new Customer(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4)
                    )
            );
        }
        return list;
    }
}
