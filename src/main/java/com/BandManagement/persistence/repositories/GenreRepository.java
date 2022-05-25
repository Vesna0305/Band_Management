package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Genre findByGenreName(String genreName);

}
