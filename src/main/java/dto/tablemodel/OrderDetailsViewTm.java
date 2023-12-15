package dto.tablemodel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsViewTm extends RecursiveTreeObject<OrderDetailsViewTm> {
    private String itemId;
    private String description;
    private double unitPrice;
    private int qty;
    private double amount;
    private JFXButton btn;

}
