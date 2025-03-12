package com.tdevred.rentals.presentation;

import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.authentication.exceptions.BadLoginException;
import com.tdevred.rentals.authentication.services.AuthenticationService;
import com.tdevred.rentals.presentation.dtos.ApiMessage;
import com.tdevred.rentals.presentation.forms.MessageForm;
import com.tdevred.rentals.services.MessageService;
import com.tdevred.rentals.services.exceptions.UnknownRentalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/messages")
public class MessageController {

    private final MessageService messageService;

    private final AuthenticationService authenticationService;

    @Autowired
    public MessageController(MessageService messageService, AuthenticationService authenticationService) {
        this.messageService = messageService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Send a message about a rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Message sent",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiMessage.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Rental does not exist", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Failed authentication", content = @Content())
    })
    @SecurityRequirement(name = "tokenAuth")
    @PostMapping
    public ResponseEntity<ApiMessage> sendMessage(
            @RequestBody MessageForm message,
            Principal authenticatedUser) throws UnknownRentalException, BadLoginException {
        User user = authenticationService.getUserByEmail(authenticatedUser.getName()).orElseThrow();

        if(user.getId() != message.getUser_id()) {
            throw new BadLoginException();
        }

        messageService.sendMessage(user, message.getMessage(), message.getRental_id());

        return ResponseEntity.ok(new ApiMessage("Message send with success"));
    }
}
