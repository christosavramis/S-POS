package net.christosav.mpos.data;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentInfo extends AbstractEntity {
    private int amountPaid;
    private Date timePaid;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType = PaymentType.getDefault();

}
