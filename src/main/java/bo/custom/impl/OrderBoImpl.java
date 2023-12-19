package bo.custom.impl;

import Controller.OrderFormController;
import Entity.OrderDetail;
import Entity.Orders;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;
import dto.OrderDetailsDto;
import dto.OrderdDto;
import dto.tablemodel.OrderViewTm;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    OrderDao orderDao= DaoFactory.getInstance().getDao(DaoType.ORDERS);
    OrderDetailsDao orderDetailsDao=DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    CustomerDao customerDao=DaoFactory.getInstance().getDao(DaoType.CUSTOMER);


    @Override
    public boolean saveOrder(OrderdDto dto) throws SQLException, ClassNotFoundException {
        boolean i=orderDao.save(new Orders(
           dto.getOrderId(),
           dto.getDate(),
           dto.getCustomerId()
        ));
        List<OrderDetailsDto> list=dto.getList();
        boolean j=false;
        for (OrderDetailsDto detailsDto:list) {
            j=orderDetailsDao.save(new OrderDetail(
                    detailsDto.getOrderId(),
                    detailsDto.getItemCode(),
                    detailsDto.getQty(),
                    detailsDto.getPrice()
            ));
        }
        return i && j;
    }

    @Override
    public boolean deleteOrder(String value) throws SQLException, ClassNotFoundException {
        return orderDao.delete(value);
    }

    @Override
    public List<OrderdDto> getAll() throws SQLException, ClassNotFoundException {
        List<Orders> ordersList=orderDao.getAll();
        List<OrderdDto> dtos=new ArrayList<>();

        for (Orders order:ordersList) {
            List<OrderDetail> list=orderDetailsDao.getOrderDetails(order.getId());
            List<OrderDetailsDto> dtoList=new ArrayList<>();
            for (OrderDetail entity:list) {
                dtoList.add(new OrderDetailsDto(
                      entity.getOrderId(),
                      entity.getItemCode(),
                      entity.getQty(),
                      entity.getUnitPrice()
                ));
            }
            dtos.add(new OrderdDto(
                    order.getId(),
                    order.getDate(),
                    order.getCustomerId(),
                    dtoList
            ));
        }

        return dtos;

    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        Orders orders = orderDao.lastOrder();
        if(orders==null){
            return "D001";
        }
        String id=orders.getId();
        int num= Integer.parseInt(id.split("D")[1]);
        return String.format("D%03d",++num);
    }

    @Override
    public List<OrderViewTm> getAllOrderView(OrderFormController ofc) throws SQLException, ClassNotFoundException {
        List<OrderViewTm> viewTmList=new ArrayList<>();
        List<Orders> entityList=orderDao.getAll();
        for (Orders order:entityList) {
            JFXButton button=new JFXButton("Delete");
            viewTmList.add(new OrderViewTm(
                    order.getId(),
                    order.getDate(),
                    order.getCustomerId(),
                    customerDao.getCustomerName(order.getCustomerId()),
                    getAmount(order.getId()),
                    button
            ));
            button.setOnAction(actionEvent -> {
                try {
                    boolean b = deleteOrder(order.getId());
                    if(b){
                        new Alert(Alert.AlertType.INFORMATION,"Succufully Deleted").show();
                        ofc.loadOrderViewTable();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        return viewTmList;

    }

    @Override
    public double getAmount(String orderId) {
        double amount = 0.0;
        try {
            List<OrderDetail> orderDetails = orderDetailsDao.getOrderDetails(orderId);
            for (OrderDetail orderDetail : orderDetails) {
                amount+=(orderDetail.getQty()) * (orderDetail.getUnitPrice());
            }
            return amount;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return amount;
    }


}
