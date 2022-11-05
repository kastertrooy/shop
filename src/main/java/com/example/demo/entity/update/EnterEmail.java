package com.example.demo.entity.update;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter@Getter
public class EnterEmail {
    @NotBlank(message = "Contact can not be empty or null")
    @Email
    private String newEmail;
}
