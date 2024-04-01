package net.christosav.mpos.data;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageEntity extends AbstractEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private ImageEntity.Type type;

    private String url;

    @Lob
    private byte[] data;

    @Getter
    public enum Type {
        PNG, JPG, JPEG, GIF;
        private final String value = this.name().toLowerCase();
    }
}
