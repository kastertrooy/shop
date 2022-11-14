package com.example.demo.securitty;

import com.example.demo.entity.CustomProfileDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public Integer getUserId (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomProfileDetails profileDetails =(CustomProfileDetails)authentication.getPrincipal();
        return profileDetails.getId();
    }
}
