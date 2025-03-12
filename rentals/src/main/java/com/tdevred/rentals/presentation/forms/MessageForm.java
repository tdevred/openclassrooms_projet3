package com.tdevred.rentals.presentation.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class MessageForm {
    @NotBlank(message = "text has to be valid")
    public String message;

    @NotNull
    @Positive(message = "id has to be valid")
    public Integer rental_id;

    @NotNull
    @Positive(message = "id has to be valid")
    public Integer user_id;
}
