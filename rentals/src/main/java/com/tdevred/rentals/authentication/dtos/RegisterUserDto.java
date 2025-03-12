package com.tdevred.rentals.authentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    @Email(message = "Invalid email")
    @NotNull(message = "No email provided")
    private String email;

    @NotNull(message = "No name provided")
    private String name;

    @NotBlank(message = "Invalid password")
    @NotNull(message = "No password provided")
    private String password;
}
