package com.BandManagement.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(length = 30, nullable = false)
    String firstName;

    @Column(length = 30, nullable = false)
    String lastName;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String passwd;

    @Column(length = 30, nullable = false)
    String address;

    @Column(length = 30, nullable = false)
    String city;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Collection<UserRole> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Orders> order;

    public Users(String firstName, String lastName, String email, String passwd, String address, String city, Collection<UserRole> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwd = passwd;
        this.address = address;
        this.city = city;
        this.roles = roles;
    }

    public void removeRole(UserRole userRole) {
        this.getRoles().remove(userRole);
        userRole.getUsers().remove(this);
    }
    public void removeRoles() {
        for (UserRole userRole : new HashSet<>(roles)) {
            removeRole(userRole);
        }
    }

}
