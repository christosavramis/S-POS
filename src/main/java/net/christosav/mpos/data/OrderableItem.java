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
    private int price;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
