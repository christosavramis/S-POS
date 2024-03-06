package net.christosav.mpos.data;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class OrderedItem extends AbstractEntity {
    @OneToOne
    private OrderableItem orderableItem;
    private int quantity;
    public int getTotal() {
        return (int) (orderableItem.getPrice() * quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedItem that)) return false;
        if (!super.equals(o)) return false;
        return quantity == that.quantity && Objects.equals(orderableItem, that.orderableItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderableItem, quantity);
    }
}
