package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileUploadRepository extends JpaRepository<Band, UUID> {

}
