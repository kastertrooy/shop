package com.example.demo.entity;

import com.example.demo.type.ProfileRole;
import com.example.demo.type.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomProfileDetails implements UserDetails {
    private Integer id;
    private String userName;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;

    private List<GrantedAuthority> authorityList;

    public CustomProfileDetails(Profile user){
        this.id = user.getId();
        this.userName = user.getName();
        this.password = user.getPassword();
        this.status = user.getProfileStatus();
        this.role = user.getProfileRole();

        this.authorityList = List.of(new SimpleGrantedAuthority(role.toString()));

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isEnabled() {
        return !status.equals(ProfileStatus.BLOCKED);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
