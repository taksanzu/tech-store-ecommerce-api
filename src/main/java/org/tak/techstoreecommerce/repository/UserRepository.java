package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tak.techstoreecommerce.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
