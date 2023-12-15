package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderDetailsDto {
    private String orderId;
    private String itemCode;
    private int qty;
    private double price;
}
