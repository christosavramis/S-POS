package net.christosav.mpos.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class Order extends AbstractEntity implements PricedEntity {
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.getDefault();

    private Date timePlaced;
    private int price;
    private boolean priceValid;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderedItem> orderedItems = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer = new Customer();


    @ManyToOne(cascade = CascadeType.ALL)
    private PaymentInfo paymentInfo = new PaymentInfo();

    /**
     * DO NOT USE THIS METHOD TO MANIPULATE THE ORDERED ITEMS.
     * Instead, use orderItem() and removeItem() to manipulate the ordered items.
     * @return the list of ordered items.
     * @see Order#orderItem(OrderableItem)
     * @see Order#removeItem(OrderedItem)
     */
    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void orderItem(OrderableItem orderableItem) {
        orderedItems.add(new OrderedItem(orderableItem, this));
        calcPrice();
    }

    public void removeItem(OrderedItem orderedItem) {
        orderedItems.remove(orderedItem);
        calcPrice();
    }



    public void calcPrice() {
        price = 0;
        for (OrderedItem orderedItem : orderedItems) {
            price += orderedItem.getTotalPrice();
        }
        priceValid = true;
    }



    public void voidOrder() {
        status = OrderStatus.VOIDED;
    }

    public void closeOrder() {
        status = OrderStatus.CLOSED;
    }


    public void payOrder() {
        status = OrderStatus.PAID;
        paymentInfo.setTimePaid(new Date());
        paymentInfo.setAmountPaid(price);
    }

    public void placeOrder() {
        timePlaced = new Date();
        status = OrderStatus.PLACED;
    }


    public String toString() {
        return "Order{" +
                "timePlaced=" + timePlaced +
                ", price=" + price +
                ", priceValid=" + priceValid +
                ", orderedItems=" + orderedItems +
                ", customer=" + customer +
                '}';
    }
}


