package com.tdevred.rentals.presentation;

import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.authentication.services.AuthenticationService;
import com.tdevred.rentals.entities.Rental;
import com.tdevred.rentals.presentation.dtos.ApiMessage;
import com.tdevred.rentals.presentation.dtos.RentalDTO;
import com.tdevred.rentals.presentation.dtos.RentalList;
import com.tdevred.rentals.services.RentalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/rentals")
public class RentalController {


    private final RentalsService rentalsService;

    private final AuthenticationService authenticationService;

    @Autowired
    public RentalController(RentalsService rentalsService, AuthenticationService authenticationService) {
        this.rentalsService = rentalsService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Get all rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All rentals retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalList.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Failed authentication", content = @Content())
    })
    @SecurityRequirement(name = "tokenAuth")
    @GetMapping
    public ResponseEntity<RentalList> getRentals() {
        return ResponseEntity.ok(new RentalList(rentalsService.getAllRentals().stream().map(this::convertToDTO).collect(Collectors.toList())));
    }
    private RentalDTO convertToDTO(Rental rental) {
        return new RentalDTO(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwnerId(), rental.getCreatedAt(), rental.getUpdatedAt());
    }

    @Operation(summary = "Add a rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Rental created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiMessage.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Failed authentication", content = @Content()),
            @ApiResponse(responseCode = "403",
                    description = "User is not authenticated", content = @Content()
            ),
    })
    @SecurityRequirement(name = "tokenAuth")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiMessage> addRental(
            @RequestParam("name") String name,
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("surface") BigDecimal surface,
            Principal authenticatedUser) {
        User user = authenticationService.getUserByEmail(authenticatedUser.getName()).orElseThrow();

        rentalsService.addRental(user, name, surface, price, picture, description);

        return ResponseEntity.ok(new ApiMessage("Rental created !"));
    }
}
