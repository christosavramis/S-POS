package net.christosav.mpos.data;

import jakarta.annotation.Nullable;
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
    private Category category = new Category();
    @Nullable
    public Category getCategory() {
        return category;
    }

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image = new ImageEntity();

    @Nullable
    public ImageEntity getImage() {
        if (image == null || image.getId() == 0) {
            return category != null ? category.getImage() : null;
        }

        return image;
    }
}
