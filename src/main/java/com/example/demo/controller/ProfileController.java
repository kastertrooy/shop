package com.example.demo.controller;

import com.example.demo.entity.create.CreateProfile;
import com.example.demo.entity.dto.ProfileDto;
import com.example.demo.entity.update.ChangePassword;
import com.example.demo.entity.update.EnterEmail;
import com.example.demo.entity.update.UpdateProfile;
import com.example.demo.servise.ProfilrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.websocket.server.PathParam;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
private final ProfilrService profilrService;

    public ProfileController(ProfilrService profilrService) {
        this.profilrService = profilrService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> newProfile(@RequestBody @Valid  CreateProfile profile){
        String result = profilrService.createProfile(profile);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/verification/{token}")
    public ResponseEntity<?> verification(@PathVariable("token") String token){
        String result = profilrService.verification(token);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/info/{email}")
    public ResponseEntity<?> info(@PathVariable("email") String email){
        ProfileDto result = profilrService.infoByEmail(email);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("id") Integer id,
                                           @RequestBody UpdateProfile updateProfile){
        ProfileDto resultProfile = profilrService.update(id,updateProfile);
        return ResponseEntity.ok(resultProfile);
    }
    @PutMapping("/changeemail/{email}")
    public ResponseEntity<?> changeEmail(@PathVariable("email")String oldEmail,
                                                  @PathParam("password") String password){
        String result = profilrService.changeEmail(oldEmail,password);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/enteremail/{token}")
    public ResponseEntity<?> enterEmail(@PathVariable("token")String token,
                                        @RequestBody() @Valid EnterEmail newEmail){
        String result = profilrService.enterEmail(token,newEmail);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/changepassword/{email}")
    public ResponseEntity<?> changePassword(@PathVariable("email")String email,
                                            @RequestBody @Valid ChangePassword changePassword){
        String result = profilrService.changePassword(email,changePassword);
        return ResponseEntity.ok(result);
    }
}
