package com.example.demo.recurse;

import com.example.demo.servise.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainResource {
    private final ProfileService profileService;

    public MainResource(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("/test")
    public String home(Model model){
      //  model.addAttribute("profile",profileService.infoByEmail("testservice.uz.zed@gmail.com"));
        System.out.println("test");
        return "home";
    }
    @GetMapping("/api/v1/registration")
    public String registration(Model model){
        //  model.addAttribute("profile",profileService.infoByEmail("testservice.uz.zed@gmail.com"));
        System.out.println("test");
        return "registration";
    }
}
