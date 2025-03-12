package com.tdevred.rentals.presentation;

import com.tdevred.rentals.authentication.dtos.UserDto;
import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get a specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Failed authentication", content = @Content()),
            @ApiResponse(responseCode = "404", description = "User does not exist", content = @Content())
    })
    @SecurityRequirement(name = "tokenAuth")
    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<UserDto> getUserById(@PathVariable int id) {
        return userService.getUserById(id).map(this::convertToDTO);
    }

    public UserDto convertToDTO(User user) {
        return new UserDto(
                user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
