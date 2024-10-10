package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tak.techstoreecommerce.model.AppRole;
import org.tak.techstoreecommerce.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRolename(AppRole appRole);

}
