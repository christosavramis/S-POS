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
public class OrderedItem extends AbstractEntity implements Priceable {
    @ManyToOne
    private Order order;

    @ManyToOne
    private OrderableItem orderableItem;

    private String name;
    private int quantity = 1;
    private int price;
    private boolean priceValid;

    @Override
    public Priceable getParent() { return order; }

    @Override
    public int getPrice() {
        if (!priceValid) {
            calcPrice();
        }

        return price;
    }

    @Override
    public int calcPriceTarget() {
        return (int) (orderableItem.getPrice() * quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (quantity <= 0) {
            order.getOrderedItems().remove(this);
        }
        modified();
    }

}
