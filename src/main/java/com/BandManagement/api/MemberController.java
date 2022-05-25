package com.BandManagement.api;

import com.BandManagement.persistence.model.Album;
import com.BandManagement.persistence.model.Band;
import com.BandManagement.persistence.model.Member;
import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.persistence.repositories.MemberRepository;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BandRepository bandRepository;

    @PostMapping("/create")
    public void createMember(@RequestBody Member member) {
        memberService.createMember(member);
    }

    @GetMapping("/get-all-members")
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMember(@RequestBody Member member, @PathVariable UUID memberId) {
        memberService.getMemberById(memberId);
    }

    @PostMapping("/assign/{memberId}/{bandId}")
    public void assignBandToMember(@PathVariable UUID memberId, @PathVariable UUID bandId) {
        memberService.assignBandToMember(memberId, bandId);
    }

    //CREATE FORMA:
    @GetMapping("/addMember")
    public String addMember(Model model) {
        model.addAttribute("member", new Member());
        model.addAttribute("bands", bandRepository.findAll());
        return "create-member";
    }

    @PostMapping("/addMember")
    public String addMember(@Valid Member member, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        member.setPhoto(fileName);
        if (result.hasErrors()) {
            return "create-member";
        }

        //band.getBandDescription().replaceAll("\\r\\n", "<br>");
        Member newMember = memberRepository.save(member);
        String uploadDir = "member-photos/" + newMember.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/" ;
    }

}
