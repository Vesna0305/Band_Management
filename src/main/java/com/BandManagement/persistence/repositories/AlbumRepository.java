package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Component
@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

    List<Album> findAll();
}
