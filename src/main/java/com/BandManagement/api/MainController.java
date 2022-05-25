package com.BandManagement.api;

import com.BandManagement.service.BandService;
import com.BandManagement.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final BandService bandService;
    private final MemberService memberService;


    public MainController(BandService bandService, MemberService memberService) {
        this.bandService = bandService;
        this.memberService = memberService;
    }

    @GetMapping
    public String getAllBands(Model model) {
        model.addAttribute("bandsList", bandService.getBands());
        model.addAttribute("membersList", memberService.getMembers());
        return "main-page";
    }

    /*@GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/")
    public String home(){
        return "index";
    }*/

}

