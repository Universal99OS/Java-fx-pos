package dto.tablemodel;

import com.jfoenix.controls.JFXButton;
import dto.ItemDto;

public class ItemTableModel extends ItemDto {
    JFXButton btn;
    public ItemTableModel(String code, String description, double uniPrice, int qtyOnHand, JFXButton btn) {
        super(code, description, uniPrice, qtyOnHand);
        this.btn=btn;
    }

    public JFXButton getBtn() {
        return btn;
    }

    public void setBtn(JFXButton btn) {
        this.btn = btn;
    }
}
