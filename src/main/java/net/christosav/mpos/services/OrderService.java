package net.christosav.mpos.services;

import lombok.RequiredArgsConstructor;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.Order;
import net.christosav.mpos.data.OrderableItem;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderCrudService orderCrudService;
    private final OrderableItemCrudService orderableItemCrudService;
    private final CategoryCrudService categoryCrudService;

    public Order placeOrder(Order order) {
        order.placeOrder();
        return orderCrudService.save(order);
    }

    public Order payOrder(Order order) {
        order.payOrder();
        return orderCrudService.save(order);
    }

    public Order placeAndPayOrder(Order order) {
        order.placeOrder();
        order.payOrder();
        return orderCrudService.save(order);
    }

    public List<Category> getCategories() {
        return categoryCrudService.list();
    }

    public List<OrderableItem> getItemsByCategory(Category category) {
        return orderableItemCrudService.findByCategory(category);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderCrudService.findById(id);
    }

}
