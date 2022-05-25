package com.BandManagement.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(length = 35, nullable = false)
    String firstName;

    @Column(length = 35, nullable = false)
    String lastName;

    @Column(unique = true)
    String email;

    @Column(nullable = false)
    String passwd;

    @Column(length = 50)
    String address;

    @Column(length = 50)
    String city;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Collection<UserRole> roles;

}
