package com.BandManagement.api;

import com.BandManagement.persistence.model.Band;
import com.BandManagement.persistence.model.Genre;
import com.BandManagement.persistence.repositories.GenreRepository;
import com.BandManagement.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreRepository genreRepository;
    private final GenreService genreService;

    @PostMapping
    public void createGenre(@RequestBody Genre genre) {
        genreService.createGenre(genre);
    }

    @GetMapping("/get-all-genres")
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }
}
