package com.example.demo.securitty;

import com.example.demo.servise.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
        )
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final ProfileEntryPointJwt profileEntryPointJwt;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfiguration(ProfileEntryPointJwt profileEntryPointJwt,
                                 JwtTokenFilter jwtTokenFilter) {
        this.profileEntryPointJwt = profileEntryPointJwt;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api/v1/profile/new").permitAll()
                .antMatchers("/api/v1/profile/getByEmail/**").permitAll()
                .antMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/users/secured/**").hasRole("ADMIN")
                .antMatchers("/api/v1/product/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                .antMatchers("/api/v1/product/secured/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/api/v1/order/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/order/getById/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        //changed not authorized request
        http.exceptionHandling().authenticationEntryPoint(profileEntryPointJwt);

        //add jwt token filter
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
/*@Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
}*/
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
