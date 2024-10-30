package org.tak.techstoreecommerce.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Timestamp
    private Date paymentDate;

    private String paymentMethod;

    private Double amount;

    private String paymentStatus;

    private String transactionId;

    @ManyToOne
    private Order order;
}
