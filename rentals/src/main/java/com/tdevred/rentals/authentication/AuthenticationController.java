package com.tdevred.rentals.authentication;

import com.tdevred.rentals.authentication.dtos.LoginResponse;
import com.tdevred.rentals.authentication.dtos.LoginUserDto;
import com.tdevred.rentals.authentication.dtos.RegisterUserDto;
import com.tdevred.rentals.authentication.dtos.UserDto;
import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.authentication.exceptions.BadLoginException;
import com.tdevred.rentals.authentication.exceptions.NonUniqueUserIdentifierException;
import com.tdevred.rentals.authentication.services.AuthenticationService;
import com.tdevred.rentals.authentication.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RequestMapping("api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))}
            ),
            @ApiResponse(responseCode = "403", description = "The email address is already used", content = @Content())
    })
    @PostMapping(value = "/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid RegisterUserDto registerUserDto) throws NonUniqueUserIdentifierException {
        User user = authenticationService.signup(registerUserDto);

        String jwtToken = jwtService.generateToken(user.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully logged in",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))}
            ),
            @ApiResponse(responseCode = "401", description = "The credentials are incorrect.", content = @Content())
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) throws BadLoginException {
        authenticationService.authenticate(loginUserDto).orElseThrow(BadLoginException::new);

        String jwtToken = jwtService.generateToken(loginUserDto.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "Get logged in user info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Failed authentication", content = @Content())
    })
    @SecurityRequirement(name = "tokenAuth")
    @GetMapping("/me")
    public ResponseEntity<UserDto> displayUser(Principal user) {
        Optional<User> userTry = authenticationService.getUserByEmail(user.getName());
        if(userTry.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User userEntity = userTry.get();

        return ResponseEntity.ok(convertToDTO(userEntity));
    }

    private UserDto convertToDTO(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public static class ValidValue {}
}