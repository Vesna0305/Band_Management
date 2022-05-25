package com.BandManagement.api;

import com.BandManagement.persistence.model.Band;
import com.BandManagement.persistence.model.Member;
import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.persistence.repositories.GenreRepository;
import com.BandManagement.persistence.repositories.MemberRepository;
import com.BandManagement.service.BandService;
import com.BandManagement.service.MemberService;
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
@RequestMapping("/bands")
public class BandController {

    private final BandService bandService;
    private final BandRepository bandRepository;
    private final GenreRepository genreRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    /*@GetMapping
    public String getAllBands(Model model) {
        model.addAttribute("bands", bandService.getBands());
        return "main-page";
    }*/

    @PostMapping
    public void createBand(@RequestBody Band band) {
        bandService.createBand(band);
    }

    @GetMapping("/get-all-bands")
    public List<Band> getBands() {
        return bandService.getBands();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBand(@RequestBody Band band, @PathVariable UUID bandId) {
        bandService.deleteBandById(bandId);
    }

    @PostMapping("/assign/{bandId}/{genreId}")
    public void assignGenreToBand(@PathVariable UUID bandId, @PathVariable UUID genreId) {
        bandService.assignGenreToBand(bandId, genreId);
    }

    @GetMapping("/band/{id}")
    public String showLandingPage(@PathVariable("id") UUID id, Model model) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("genre", genreRepository.findAll());
        return "band-landing-page";
    }

    //ABOUT:
    @GetMapping("/about/{id}")
    public String aboutPage(@PathVariable("id") UUID id, Model model) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("bandsList", bandService.getBands());
        model.addAttribute("genre", genreRepository.findAll());
        return "about";
    }

    //MEMBERS:
    @GetMapping("/member/{id}")
    public String showMembersPage(@PathVariable("id") UUID id, Model model) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        model.addAttribute("band", band.getMember());
        model.addAttribute("bandList", bandService.getBands());
        model.addAttribute("member", memberRepository.findAll());
        return "members";
    }

    //CREATE FORMA:
    @GetMapping("/addBand")
    public String addBand(Model model) {
        model.addAttribute("band", new Band());
        model.addAttribute("genres", genreRepository.findAll());
        return "create-band";
    }

    @PostMapping("/addBand")
    public String addBand(@Valid Band band, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException{
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        band.setPhoto(fileName);
        if (result.hasErrors()) {
            return "create-band";
        }
        //band.getBandDescription().replaceAll("\\r\\n", "<br>");
        Band newBand = bandRepository.save(band);
        String uploadDir = "band-photos/" + newBand.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/" ;
    }

}
