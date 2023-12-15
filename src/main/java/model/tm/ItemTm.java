package model.tm;

import com.jfoenix.controls.JFXButton;
import model.Item;

public class ItemTm extends Item{
    JFXButton btn;
    public ItemTm(String code, String description, double uniPrice, int qtyOnHand,JFXButton btn) {
        super(code, description, uniPrice, qtyOnHand);
        this.btn=btn;
    }

    public JFXButton getBtn() {
        return btn;
    }
}
