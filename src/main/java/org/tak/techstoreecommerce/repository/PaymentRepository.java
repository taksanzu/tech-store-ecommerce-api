package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tak.techstoreecommerce.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
