package net.christosav.mpos.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.function.Predicate.not;

@Entity(name = "orders")
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class Order extends AbstractEntity implements Priceable {

    private Date date = new Date();

    // Pricing
    private int price;

    private boolean priceValid;
    public boolean isPriceValid() {
        return priceValid && getPriceableChildren().stream().anyMatch(not(Priceable::isPriceValid));
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderedItem> orderedItems = new ArrayList<>();

    public void orderItem(OrderableItem orderableItem) {
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setName(orderableItem.getName());
        orderedItem.setOrder(this);
        orderedItem.setOrderableItem(orderableItem);
        orderedItems.add(orderedItem);
        modified();
    }

    @Override
    public int getPrice() {
        if (!isPriceValid()) {
            calcPrice();
        }

        return price;
    }

    @Override
    public List<Priceable> getPriceableChildren() { return new ArrayList<>(orderedItems); }


    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    private Date timePlaced;



    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", price=" + price +
                ", priceValid=" + priceValid +
                ", orderedItems=" + orderedItems +
                ", customer=" + customer +
                ", timePlaced=" + timePlaced +
                '}';
    }
}


