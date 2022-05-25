package com.BandManagement.service;

import com.BandManagement.dto.BandGenreDto;
import com.BandManagement.persistence.model.Band;
import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.persistence.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
@Service
public class BandService {

    private final BandRepository bandRepository;
    private final GenreRepository genreRepository;

    public List<BandGenreDto> getAllBands() {
        return bandRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private BandGenreDto convertEntityToDto(Band band) {
        BandGenreDto bandGenreDto = new BandGenreDto();
        bandGenreDto.setBand_id(band.getId());
        bandGenreDto.setBandName(band.getBandName());
        bandGenreDto.setYear(band.getYear());
        bandGenreDto.setGenreName(band.getGenre().getGenreName());
        return bandGenreDto;
    }

    public Band createBand(Band band) {
        return bandRepository.save(band);
    }

    public List<Band> getBands() {
        return bandRepository.findAll();
    }

    public void deleteBandById(UUID id) {
        bandRepository.deleteById(id);
    }

    public void assignGenreToBand(UUID bandId, UUID genreId) {
        var band = bandRepository.findById(bandId).orElse(null);
        var genre = genreRepository.findById(genreId).orElse(null);

        band.setGenre(genre);

        bandRepository.save(band);
    }

    public Band getBandById(UUID id) {
        return bandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
    }

}
