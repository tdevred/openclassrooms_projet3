package com.tdevred.rentals.presentation.dtos;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
public class RentalDTO {

    public int id;
    public String name;
    public BigDecimal surface;
    public BigDecimal price;
    public String picture;
    public String description;
    public int owner_id;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}
