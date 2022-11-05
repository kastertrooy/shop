package com.example.demo.servise;

import com.example.demo.entity.Profile;
import com.example.demo.entity.create.CreateProfile;
import com.example.demo.entity.dto.ProfileDto;
import com.example.demo.entity.update.ChangePassword;
import com.example.demo.entity.update.EnterEmail;
import com.example.demo.entity.update.UpdateProfile;
import com.example.demo.excaption.BadRequest;
import com.example.demo.repo.ProfileRepo;
import com.example.demo.type.ProfileRole;
import com.example.demo.type.ProfileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class ProfilrService {
    @Value("${serverAddress}")
    private String serverAddress;
    @Autowired private JwtTokenFilter jwtToken;
    private final ProfileRepo profileRepo;
    @Autowired private MailSenderService mailSender;
    //private final PasswordEncoder passwordEncoder;//todo encoder

    @Value("${serverAddress}")
    private String serverAddres;

    public ProfilrService(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    public String createProfile(CreateProfile createProfile) {
        Profile profile = profileRepo.findByEmail(createProfile.getEmail());
        if (profile != null){
            return "This email already exists!";
        }
        profile = new Profile();
        profile.setName(createProfile.getName());
        profile.setContact(createProfile.getContact());
        profile.setEmail(createProfile.getEmail());
       // profile.setPassword(passwordEncoder.encode(createProfile.getPassword()));//todo password encoder
        profile.setPassword(createProfile.getPassword());
        profile.setCreateAt(LocalDateTime.now());
        profile.setProfileStatus(ProfileStatus.INACTIVE);
        profile.setProfileRole(ProfileRole.USER);
        profileRepo.save(profile);
        profile = profileRepo.findByEmail(profile.getEmail());
        if (mailSender.send(profile)){return "Please confirm your email!";}
        else throw new BadRequest("Email not delivered!");
    }

    public String verification(String token) {
        Profile profile = profileRepo.getById(jwtToken.getUserID(token));
        if (profile == null){
            return "profile not found";
        }
        if (profile.getProfileStatus().equals(ProfileStatus.BLOCKED)){
            return "Your profile is blocked";
        }
        if (jwtToken.getExpirationDate(token).after(new Date(System.currentTimeMillis()))){
            profile.setProfileStatus(ProfileStatus.ACTIVE);
            profileRepo.save(profile);
            return "Successful verified";
        }
       return "please return verified";
    }
    private Profile findProfileById (Integer id){
        Optional<Profile>optionalProfile = profileRepo.findById(id);
        if (optionalProfile.isEmpty()){
            throw new BadRequest(String.format("Profil by id: %s not found",id));
        }
        return optionalProfile.get();
    }
    private Profile findProfileByEmail (String email){
        Optional<Profile>optionalProfile = profileRepo.findByEmailAndDeletedAtIsNull(email);
        if (optionalProfile.isEmpty()){
            throw new BadRequest(String.format("Profil by email: %s not found",email));
        }
        return optionalProfile.get();
    }
    public ProfileDto infoByEmail(String email) {
        return getProfilDto(findProfileByEmail(email));
    }

    private ProfileDto getProfilDto(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setEmail(profile.getEmail());
        dto.setContact(profile.getContact());
        dto.setName(profile.getName());
        dto.setImageId(profile.getImageId());
        return dto;
    }

    public ProfileDto update(Integer id,UpdateProfile updateProfile) {
        Profile profile = findProfileById(id);
        if (updateProfile.getImageId() != null){
            profile.setImageId(updateProfile.getImageId());
        }
        if (updateProfile.getContact() != null){
            profile.setContact(updateProfile.getContact());
        }
        if (updateProfile.getName() != null){
            profile.setName(updateProfile.getName());
        }
        profileRepo.save(profile);
        return getProfilDto(profile);
    }

    public String changeEmail(String oldEmail, String password) {
        Profile profile = findProfileByEmail(oldEmail);
        if (!profile.getPassword().equals(password)){
            throw new BadRequest("Password is wrong");
        }
        String token = jwtToken.createTokenForChangeEmail(profile);
        String link = String.format("Click for verification this link: %sapi/v1/profile/enteremail/%s",serverAddress,token);
        return link;

    }

    public String changePassword(String email, ChangePassword changePassword) {
        Profile profile = findProfileByEmail(email);
        if (!profile.getPassword().equals(changePassword.getOldPassword())){
            throw new BadRequest("Old password is wrong");
        }
        profile.setPassword(changePassword.getNewPassword());
        profileRepo.save(profile);
        return "Password changed";
    }

    public String enterEmail(String token, EnterEmail newEmail) {
        Profile profile = findProfileById(jwtToken.getUserID(token));
        profile.setProfileStatus(ProfileStatus.INACTIVE);
        profile.setEmail(newEmail.getNewEmail());
        profileRepo.save(profile);
        if (mailSender.send(profile)){
            return "Please confirm your email";
        }
        else throw new BadRequest("Email not delivered!");
    }
}
