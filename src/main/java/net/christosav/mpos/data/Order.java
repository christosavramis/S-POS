package net.christosav.mpos.data;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "orders")
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class Order extends AbstractEntity {
    private Date date = new Date();

    @OneToOne
    private AddressDetails addressDetails;

    @OneToMany
    private List<OrderedItem> orderedItems = new ArrayList<>();

    public int getTotal() {
        return orderedItems.stream().mapToInt(OrderedItem::getTotal).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(date, order.date) && Objects.equals(addressDetails, order.addressDetails) && Objects.equals(orderedItems, order.orderedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, addressDetails, orderedItems);
    }
}


