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
    private String road;
    private String number;
    private String city;
}
