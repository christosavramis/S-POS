package net.christosav.mpos.services;

import lombok.RequiredArgsConstructor;
import net.christosav.mpos.data.Order;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderCrudService orderCrudService;

    public Order placeOrder(Order order) {
        order.setTimePlaced(new Date());

        return orderCrudService.save(order);
    }

}
