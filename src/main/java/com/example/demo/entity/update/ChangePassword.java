package com.example.demo.entity.update;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ChangePassword {
private String oldPassword;
@NotBlank(message = "New password can not be empty or null")
@Size(min = 8 ,max = 240 ,message = ("Min 8 max 240 characters!"))
private String newPassword;
}
