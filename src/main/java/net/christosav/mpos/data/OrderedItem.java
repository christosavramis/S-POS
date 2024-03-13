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

}
