package com.tdevred.rentals.presentation.forms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RentalModifyForm {
    BigDecimal surface;

    BigDecimal price;

    String name;

    String description;
}
