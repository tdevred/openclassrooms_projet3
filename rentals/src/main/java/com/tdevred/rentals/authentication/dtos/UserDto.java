package com.tdevred.rentals.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserDto {
    public int id;
    public String name;
    public String email;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
