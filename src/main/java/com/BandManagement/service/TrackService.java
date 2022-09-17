package com.BandManagement.service;

import com.BandManagement.persistence.model.Track;
import com.BandManagement.persistence.repositories.AlbumRepository;
import com.BandManagement.persistence.repositories.TrackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> getTracks() {
        return trackRepository.findAll();
    }

    public void deleteTracksById(UUID id) {
        trackRepository.deleteById(id);
    }

    public void assignAlbumToTrack(UUID trackId, UUID albumId) {
        var track = trackRepository.findById(trackId).orElse(null);
        var album = albumRepository.findById(albumId).orElse(null);

        track.setAlbum(album);

        trackRepository.save(track);
    }

    public Track getTrackById(UUID id) {
        return trackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
    }
}
