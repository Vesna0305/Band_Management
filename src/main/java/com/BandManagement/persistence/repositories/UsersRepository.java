package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Component
@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    Users findByEmail(String email);
    Users findByFirstName(String firstName);
}
