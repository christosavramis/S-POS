package net.christosav.mpos.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractEntity {
    private String name;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image = new ImageEntity();

    @Nullable
    public ImageEntity getImage() {
        return image;
    }
}
