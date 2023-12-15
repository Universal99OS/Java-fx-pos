package dto.tablemodel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
public class OrderViewTm extends RecursiveTreeObject<OrderViewTm> {
    private String orderId;
    private String date;
    private String customerId;
    private String name;
    private double amount;
    private JFXButton btn;

}
