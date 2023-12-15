package model.impl;

import db.DbConnector;
import dto.OrderDetailsDto;
import model.OrderDetailsModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModelImpl implements OrderDetailsModel {

    @Override
    public boolean saveOrderDtails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isOrderDetailsSaved=true;
        for (OrderDetailsDto dto:list) {
            String sql="INSERT INTO orderdetail VALUES(?,?,?,?)";
            PreparedStatement pstm = DbConnector.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQty());
            pstm.setDouble(4,dto.getPrice());

            if(!(pstm.executeUpdate()>0)){
                isOrderDetailsSaved=false;
            }
        }
        return isOrderDetailsSaved;

    }
}
