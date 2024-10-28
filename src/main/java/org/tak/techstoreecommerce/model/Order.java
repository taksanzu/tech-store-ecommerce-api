package org.tak.techstoreecommerce.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderId;

    @Timestamp
    private Date orderDate;

    private String orderStatus;

    private Long totalAmount;

    private String shippingAddress;

    private String billingAddress;

    private String paymentMethod;

    private String paymentStatus;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
