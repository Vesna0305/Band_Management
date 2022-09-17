package com.BandManagement.service;


import com.BandManagement.persistence.model.Genre;
import com.BandManagement.persistence.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Component
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

}
