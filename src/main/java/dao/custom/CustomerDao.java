package dao.custom;

import Entity.Customer;
import dao.CrudDao;
import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<Customer> {
//    boolean saveCustomer(CustomerDto dto) throws SQLException,ClassNotFoundException;
//    boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
//    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
//
//    List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException;

    Customer searchCustomer(String id);

    String getCustomerName(String customerId);


}
