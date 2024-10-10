package org.tak.techstoreecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(name = "rolename")
    private AppRole rolename;

    public Role(AppRole roleName) {
        this.rolename = roleName;
    }

}
