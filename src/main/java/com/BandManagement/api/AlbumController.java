package com.BandManagement.api;

import com.BandManagement.persistence.model.*;
import com.BandManagement.persistence.repositories.AlbumRepository;
import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.persistence.repositories.TrackRepository;
import com.BandManagement.service.AlbumService;
import com.BandManagement.service.BandService;
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
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final AlbumService albumService;
    private final BandRepository bandRepository;
    private final TrackRepository trackRepository;
    private final BandService bandService;

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

    //CREATE FORMA NEW ALBUM:
    @GetMapping("/addAlbum")
    public String addAlbum(Model model) {
        model.addAttribute("album", new Album());
        model.addAttribute("bands", bandRepository.findAll());

        return "create-album";
    }

    @PostMapping("/addAlbum")
    public String addAlbum(@Valid Album album, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        album.setPhoto(fileName);
        if (result.hasErrors()) {
            return "create-album";
        }

        //band.getBandDescription().replaceAll("\\r\\n", "<br>");
        Album newAlbum = albumRepository.save(album);
        String uploadDir = "album-photos/" + newAlbum.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/";
    }

    //TRACKS:
    @GetMapping("/tracks/{id}")
    public String showTracksPage(@PathVariable("id") UUID id, Model model, Track track, String trackName) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("album", album);
        model.addAttribute("albumList", albumRepository.getById(id));

        model.addAttribute("track", track);
        /*model.addAttribute("tracks", trackRepository.getTrackByAlbumId(id));*/
        model.addAttribute("tracks", trackRepository.findAllByAlbumIdOrderByTrackNameAsc(id));

        return "tracks";
    }

    //EDIT:
    @PostMapping("update/{id}")
    public String updateAlbums(@PathVariable("id") UUID id, @Valid Album album, @RequestParam("image") MultipartFile multipartFile, BindingResult result,
                               Model model) throws IOException {
        if (result.hasErrors()) {
            album.setId(id);
            return "redirect:http://localhost:8080/";
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        album.setPhoto(fileName);
        String uploadDir = "album-photos/" + album.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        albumRepository.save(album);
        model.addAttribute("album", albumRepository.findAll());
        model.addAttribute("band", bandRepository.findAll());
        return "redirect:http://localhost:8080/";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        model.addAttribute("album", album);
        model.addAttribute("band", bandRepository.findAll());

        return "album-edit";
    }

}
