package dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderdDto {
    private String orderId;
    private String date;
    private String customerId;
    private List<OrderDetailsDto> list;
}
