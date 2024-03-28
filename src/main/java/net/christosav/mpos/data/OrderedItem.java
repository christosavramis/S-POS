package net.christosav.mpos.data;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class OrderedItem extends AbstractEntity implements PricedEntity {
    @ManyToOne
    private Order order;

    @ManyToOne
    private OrderableItem orderableItem;

    private String name;
    private int quantity = 1;

    private int price;
    private boolean priceValid;

    public OrderedItem(OrderableItem orderableItem, Order order) {
        this.order = order;
        this.orderableItem = orderableItem;
        this.name = orderableItem.getName();
        calcPrice();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.priceValid = false;

        if (quantity <= 0) {
            order.removeItem(this);
        } else {
            order.calcPrice();
        }
    }

    @Override
    public void calcPrice() {
        price = orderableItem.getPrice() * quantity;
        priceValid = true;
    }

}
