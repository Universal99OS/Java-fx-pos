package dao.custom.impl;

import Entity.Customer;
import dao.custom.CustomerDao;
import dao.util.CrudUtil;
import dao.util.HybernateUtil;
import db.DbConnector;
import dto.CustomerDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
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

        Session session = HybernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;

//        String sql="INSERT INTO customer VALUES(?,?,?,?)";
//        return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());

    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {

        Session session = HybernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.find(Customer.class, entity.getId());
        customer.setName(entity.getName());
        customer.setSalary(entity.getSalary());
        customer.setAddress(entity.getAddress());
        session.save(customer);
        transaction.commit();
        session.close();
        return true;

//        String sql="Update customer SET name=?, address=?, salary=? WHERE id=?";
//
//        return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());

    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {

        Session session = HybernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Customer.class,value));
        transaction.commit();
        session.close();
        return true;

//        String sql="DELETE FROM customer WHERE id=?";
//        return CrudUtil.execute(sql,value);
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {

        Session session = HybernateUtil.getSession();
        Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.getResultList();

        return list;


//        String sql="SELECT * FROM customer";
//        ResultSet rst = CrudUtil.execute(sql);
//
//        List<Customer> list= new ArrayList<>();
//
//        while (rst.next()){
//            list.add(
//                    new Customer(
//                            rst.getString(1),
//                            rst.getString(2),
//                            rst.getString(3),
//                            rst.getDouble(4)
//                    )
//            );
//        }
//        return list;
    }
}
