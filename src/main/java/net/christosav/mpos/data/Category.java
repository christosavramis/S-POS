package net.christosav.mpos.data;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractEntity {
    private String name;
    private boolean active;
}
