package com.BandManagement.api;

import com.BandManagement.persistence.model.Album;
import com.BandManagement.persistence.model.Track;
import com.BandManagement.persistence.repositories.AlbumRepository;
import com.BandManagement.persistence.repositories.TrackRepository;
import com.BandManagement.service.AlbumService;
import com.BandManagement.service.TrackService;
import com.BandManagement.util.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/tracks")
public class TrackController {

    private final TrackRepository trackRepository;
    private final TrackService trackService;
    private final AlbumRepository albumRepository;
    private final AlbumService albumService;

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

    //CREATE FORMA NEW TRACK:
    @GetMapping("/addTrack/{id}")
    public String addTrack(@PathVariable UUID id, Model model) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        model.addAttribute("album", album);
        model.addAttribute("track", new Track());
        model.addAttribute("albums", albumRepository.findById(id));

        return "create-track";
    }

    @PostMapping("/addTrack/{id}")
    public String addTrack(@PathVariable UUID id, @Valid Track track, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "create-track";
        }
        Album album = albumRepository.findById(id).orElse(null);
        track.setAlbum(album);

        trackRepository.save(track);

        return "redirect:/" ;
    }

    //EDIT:
    @PostMapping("update/{id}")
    public String updateTracks(@PathVariable("id") UUID id, @Valid Track track, BindingResult result,
                               Model model) throws IOException {
        if (result.hasErrors()) {
            track.setId(id);
            return "redirect:viewPage";
        }

        trackRepository.save(track);
        model.addAttribute("track", trackRepository.findAll());
        model.addAttribute("album", albumRepository.findAll());
        model.addAttribute("tracks", trackRepository.findAll());
        return "redirect:http://localhost:8080/";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        model.addAttribute("track", track);
        model.addAttribute("album", albumRepository.findAll());

        return "tracks-edit";
    }

}
