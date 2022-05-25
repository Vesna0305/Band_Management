package com.BandManagement.api;


import com.BandManagement.persistence.model.Album;
import com.BandManagement.persistence.model.Band;
import com.BandManagement.persistence.repositories.AlbumRepository;
import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final AlbumService albumService;
    private final BandRepository bandRepository;

    @PostMapping("/create")
    public void createAlbum(@RequestBody Album album) {
        albumService.createAlbum(album);
    }

    @GetMapping("/get-all-albums")
    public List<Album> getAlbums() {
        return albumService.getAlbums();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbum(@RequestBody Album album, @PathVariable UUID albumId) {
        albumService.getAlbumById(albumId);
    }

    @PostMapping("/assign/{albumId}/{bandId}")
    public void assignBandToAlbum(@PathVariable UUID albumId, @PathVariable UUID bandId) {
        albumService.assignBandToAlbum(albumId, bandId);
    }

}
