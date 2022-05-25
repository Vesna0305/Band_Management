package com.BandManagement.api;

import com.BandManagement.persistence.model.Album;
import com.BandManagement.persistence.model.Track;
import com.BandManagement.persistence.repositories.AlbumRepository;
import com.BandManagement.persistence.repositories.TrackRepository;
import com.BandManagement.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackRepository trackRepository;
    private final TrackService trackService;
    private final AlbumRepository albumRepository;

    @PostMapping("/create")
    public void createTrack(@RequestBody Track track) {
        trackService.createTrack(track);
    }

    @GetMapping("/get-all-tracks")
    public List<Track> getTracks() {
        return trackService.getTracks();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTrack(@RequestBody Track track, @PathVariable UUID trackId) {
        trackService.getTrackById(trackId);
    }

    @PostMapping("/assign/{trackId}/{albumId}")
    public void assignAlbumToTrack(@PathVariable UUID trackId, @PathVariable UUID albumId) {
        trackService.assignAlbumToTrack(trackId, albumId);
    }
}
