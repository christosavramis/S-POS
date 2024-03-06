package net.christosav.mpos.data;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderableItem extends AbstractEntity {
    private String name;
    private String description;
    private double price;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderableItem orderableItem)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(price, orderableItem.price) == 0 && Objects.equals(name, orderableItem.name) && Objects.equals(description, orderableItem.description) && Objects.equals(category, orderableItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, category);
    }
}
