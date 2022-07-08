package dto.offerhistory;

import java.util.List;

public class AllOrdersToShowDto {
    private List<OrderToShowDto> orders;

    public AllOrdersToShowDto(List<OrderToShowDto> orders) {
        this.orders = orders;
    }

    public List<OrderToShowDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderToShowDto> orders) {
        this.orders = orders;
    }
}
