package com.BandManagement.api;

import com.BandManagement.service.BandService;
import com.BandManagement.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final BandService bandService;

    public MainController(BandService bandService, MemberService memberService) {
        this.bandService = bandService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String getAllBands(Model model) {
        model.addAttribute("bandsList", bandService.getBands());
        return "main-page";
    }

}

