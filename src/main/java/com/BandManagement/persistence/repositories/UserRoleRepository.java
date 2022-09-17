package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Component
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

        public UserRole findByRoleName(String roleName);

}
