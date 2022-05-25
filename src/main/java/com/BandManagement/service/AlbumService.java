package com.BandManagement.service;

import com.BandManagement.persistence.model.Album;
import com.BandManagement.persistence.repositories.AlbumRepository;
import com.BandManagement.persistence.repositories.BandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final BandRepository bandRepository;

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    public void deleteAlbumsById(UUID id) {
        albumRepository.deleteById(id);
    }

    public void assignBandToAlbum(UUID albumId, UUID bandId) {
        var album = albumRepository.findById(albumId).orElse(null);
        var band = bandRepository.findById(bandId).orElse(null);

        album.setBand(band);

        albumRepository.save(album);
    }

    public Album getAlbumById(UUID id) {
        return albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
    }

}
