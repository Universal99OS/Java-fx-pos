package Entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Embeddable
public class OrderDetailsKey implements Serializable {

    @Column (name = "orderId",nullable = false)
    private String orderId;

    @Column(name="itemCode",nullable = false)
    private String itemCode;


}
