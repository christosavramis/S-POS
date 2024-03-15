package net.christosav.mpos.data;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer extends AbstractEntity {
    private String name;
    private String phone;

    @ManyToOne
    private AddressDetails addressDetails;

}
