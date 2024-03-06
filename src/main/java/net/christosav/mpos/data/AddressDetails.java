package net.christosav.mpos.data;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDetails extends AbstractEntity {
    private String address;
    private String city;
    private String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDetails that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, city, postalCode);
    }
}
