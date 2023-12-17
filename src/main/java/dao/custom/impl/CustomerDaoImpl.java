package dao.custom.impl;

import Entity.Customer;
import dao.custom.CustomerDao;
import dao.util.CrudUtil;
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
    public String getCustomerName(String customerId) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM customer WHERE id=?";

        ResultSet resultSet = CrudUtil.execute(sql,customerId);
        resultSet.next();
        return resultSet.getString(2);

    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO customer VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        String sql="Update customer SET name=?, address=?, salary=? WHERE id=?";

        return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());

    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM customer WHERE id=?";
        return CrudUtil.execute(sql,value);
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM customer";
        ResultSet rst = CrudUtil.execute(sql);

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
